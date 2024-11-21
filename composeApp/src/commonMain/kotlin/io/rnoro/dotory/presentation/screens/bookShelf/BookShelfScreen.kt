package io.rnoro.dotory.presentation.screens.bookShelf

import Genre
import StoryBook
import StoryBookResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.components.BookCard
import io.rnoro.dotory.presentation.navigation.NavigationViewModel

@Composable
expect fun WindowSizeWrapper(content: @Composable (WindowSizeClass) -> Unit)

@Composable
fun BookShelfScreen(
    navigationViewModel: NavigationViewModel,
    navController: NavHostController
) {
    WindowSizeWrapper { windowSizeClass ->
        BookShelfScreenContent(
            windowSizeClass = windowSizeClass,
            viewModel = navigationViewModel,
            navController = navController
        )
    }
}

@Composable
fun BookShelfScreenContent(
    windowSizeClass: WindowSizeClass,
    viewModel: NavigationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val booksByGenre = remember {
        StoryBookResources.allBooks.groupBy { it.genre }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 24.dp, bottom = 100.dp)
    ) {
        Text(
            text = "나의 책장",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        booksByGenre.forEach { (genre, books) ->
            if (books.isNotEmpty()) {
                GenreSection(
                    genre = genre,
                    books = books,
                    windowSizeClass = windowSizeClass,
                    onBookClick = { book ->
                        viewModel.navigateToFairyTale(navController, book.id, isFromBookshelf = true)
                    }
                )
            }
        }
    }
}

@Composable
private fun GenreSection(
    genre: Genre,
    books: List<StoryBook>,
    windowSizeClass: WindowSizeClass,
    onBookClick: (StoryBook) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 장르 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = genre.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = genre.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${books.size}권",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // 수정된 BookCarousel (자동 스크롤 없음)
        ModifiedBookCarousel(
            books = books,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.fillMaxWidth(),
            onBookClick = onBookClick
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun ModifiedBookCarousel(
    books: List<StoryBook>,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    onBookClick: (StoryBook) -> Unit
) {
    val cardSize = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 160.dp
        WindowWidthSizeClass.Medium -> 200.dp
        WindowWidthSizeClass.Expanded -> 240.dp
        else -> 200.dp
    }

    val pagerState = rememberPagerState(pageCount = {
        (books.size + getCardsPerPage(windowSizeClass) - 1) / getCardsPerPage(windowSizeClass)
    })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSpacing = 16.dp,
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) { page ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,  // 왼쪽 정렬로 변경
                verticalAlignment = Alignment.CenterVertically
            ) {
                val startIndex = page * getCardsPerPage(windowSizeClass)
                val endIndex = minOf(startIndex + getCardsPerPage(windowSizeClass), books.size)

                for (index in startIndex until endIndex) {
                    BookCard(
                        book = books[index],
                        modifier = Modifier
                            .size(cardSize)
                            .padding(end = 16.dp),  // 오른쪽 패딩으로 카드 간격 조정
                        onClick = { onBookClick(books[index]) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 페이지 인디케이터
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(
                            color = if (pagerState.currentPage == index)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

private fun getCardsPerPage(windowSizeClass: WindowSizeClass): Int {
    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 5
        else -> 3
    }
}