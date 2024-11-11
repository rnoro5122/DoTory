package compose.project.dotory.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.book_classic1
import dotory.composeapp.generated.resources.book_classic2
import dotory.composeapp.generated.resources.book_classic3
import dotory.composeapp.generated.resources.book_classic4
import dotory.composeapp.generated.resources.book_family1
import dotory.composeapp.generated.resources.book_family2
import dotory.composeapp.generated.resources.book_family3
import dotory.composeapp.generated.resources.book_family4
import dotory.composeapp.generated.resources.book_fantasy1
import dotory.composeapp.generated.resources.book_fantasy2
import dotory.composeapp.generated.resources.book_fantasy3
import dotory.composeapp.generated.resources.book_fantasy4

@Composable
fun BookShelfScreen(navController: NavController, selectedItem: MutableState<Int>) {
    val overlayImages1 = listOf(
        Res.drawable.book_fantasy3,
        Res.drawable.book_fantasy2,
        Res.drawable.book_fantasy1,
        Res.drawable.book_fantasy4,
    )
    val overlayImages2 = listOf(
        Res.drawable.book_classic1,
        Res.drawable.book_classic2,
        Res.drawable.book_classic3,
        Res.drawable.book_classic4,
    )
    val overlayImages3 = listOf(
        Res.drawable.book_family1,
        Res.drawable.book_family2,
        Res.drawable.book_family3,
        Res.drawable.book_family4,
    )
    val bookNames1 = listOf("숲지키미 한경", "친환경 방랑자", "재활용 지팡이", "모여봐요 쓰레기숲")
    val bookNames2 = listOf("재활용 왕국", "조선시대 속 분리수거", "쓰레기주면 안잡아먹지", "더러워진 우물")
    val bookNames3 = listOf("숲 속 청소", "친환경 캠핑", "가족 환경교육", "가족이 최고야")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "내가 만든 소중한 동화책들",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "장르 : 판타지",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            BookShelf(overlayImages1, bookNames1, navController)
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "장르 : 고전",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            BookShelf(overlayImages2, bookNames2, navController)
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "장르 : 가족",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            BookShelf(overlayImages3, bookNames3, navController)
        }
    }
}