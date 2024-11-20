package compose.project.dotory.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import compose.project.dotory.domain.models.Book
import compose.project.dotory.platform.rememberWindowInfo
import compose.project.dotory.util.pageTransitionAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun BookCarousel(
    books: List<Book>,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit
) {
    val windowInfo = rememberWindowInfo()
    val screenWidth = windowInfo.screenWidthDp.dp

    // 화면 크기에 따른 카드 스타일 설정
    val cardStyle = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CardStyle(
                width = 240.dp,
                height = 240.dp,
                horizontalPadding = 12.dp,
                pageSpacing = 12.dp
            )
        }
        WindowWidthSizeClass.Medium -> {
            CardStyle(
                width = 280.dp,
                height = 280.dp,
                horizontalPadding = 16.dp,
                pageSpacing = 16.dp
            )
        }
        else -> {
            CardStyle(
                width = 320.dp,
                height = 320.dp,
                horizontalPadding = 20.dp,
                pageSpacing = 20.dp
            )
        }
    }

    // 실제 화면 너비로 들어갈 수 있는 카드 개수 계산
    val availableWidth = screenWidth - (cardStyle.horizontalPadding * 2)
    val cardsPerPage = maxOf(1,
        (availableWidth / (cardStyle.width + cardStyle.pageSpacing)).toInt()
    )

    val pageCount = (books.size + (cardsPerPage - 1)) / cardsPerPage
    val pagerState = rememberPagerState(pageCount = { pageCount })
    var isUserScrolling by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isUserScrolling) {
        if (!isUserScrolling) {
            while (true) {
                delay(2500)
                try {
                    val nextPage = (pagerState.currentPage + 1) % pageCount
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(
                            durationMillis = 800,
                            easing = FastOutSlowInEasing
                        )
                    )
                } catch (e: CancellationException) {
                    break
                }
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.height(cardStyle.height + cardStyle.horizontalPadding * 2),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { isUserScrolling = true },
                            onDragEnd = {
                                coroutineScope.launch {
                                    delay(2500)
                                    isUserScrolling = false
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                            }
                        )
                    },
                pageSpacing = cardStyle.pageSpacing,
                contentPadding = PaddingValues(horizontal = cardStyle.horizontalPadding)
            ) { page ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val startIndex = page * cardsPerPage
                    repeat(cardsPerPage) { offset ->
                        val index = startIndex + offset
                        if (index < books.size) {
                            BookCard(
                                book = books[index],
                                modifier = Modifier
                                    .width(cardStyle.width)
                                    .height(cardStyle.height)
                                    .padding(horizontal = cardStyle.horizontalPadding)
                                    .pageTransitionAnimation(pagerState, page),
                                onClick = { onBookClick(books[index]) }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PageIndicator(pageCount = pageCount, currentPage = pagerState.currentPage)
    }
}
