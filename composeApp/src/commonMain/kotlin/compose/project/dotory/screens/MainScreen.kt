package compose.project.dotory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.book_classic1
import dotory.composeapp.generated.resources.book_classic2
import dotory.composeapp.generated.resources.book_fantasy1
import dotory.composeapp.generated.resources.book_fantasy2
import dotory.composeapp.generated.resources.bookshelf
import dotory.composeapp.generated.resources.bookside
import dotory.composeapp.generated.resources.tree
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(navController: NavController, selectedItem: MutableState<Int>) {
    val mainPainter = painterResource(
        resource = Res.drawable.tree
    )
    val overlayImages = listOf(
        Res.drawable.book_fantasy1,
        Res.drawable.book_fantasy2,
        Res.drawable.book_classic1,
        Res.drawable.book_classic2,
    )
    val bookNames = listOf("재활용 지팡이", "친환경 방랑자", "재활용 왕국", "조선시대 분리수거")
    Column(
        modifier = Modifier.statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "세상에 단 하나뿐인 나만의 동화책",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = mainPainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Text(
            text = "도토리(DoTory)",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        BookShelf(overlayImages, bookNames, navController)
        Text(
            text = "이 책들을 가장 재미있게 읽었어요!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.offset(y = (-20).dp),
            fontSize = 15.sp
        )
    }
}

@Composable
fun BookShelf(
    overlayImages: List<DrawableResource>,
    bookNames: List<String>,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(230.dp)
    ) {
        Image(
            painter = painterResource(
                resource = Res.drawable.bookshelf
            ),
            contentDescription = null,
            modifier = Modifier.width(380.dp).height(200.dp).padding(horizontal = 5.dp)
                .align(Alignment.BottomCenter),
        )
        LazyRow(
            modifier = Modifier.align(Alignment.TopCenter).fillMaxSize()
                .clip(RectangleShape).padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(overlayImages) { index, imageRes ->
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(200.dp) // Box 크기 조정
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("DetailScreen")
                                }
                        ) {
                            Image(
                                painter = painterResource(resource = imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(107.dp)
                                    .height(135.dp),
                                contentScale = ContentScale.Crop
                            )
                            Image(
                                painter = painterResource(resource = Res.drawable.bookside),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(140.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = bookNames.getOrNull(index)
                                ?: "", // bookNames의 해당 항목 표시, 없는 경우 빈 문자열
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
    }
}