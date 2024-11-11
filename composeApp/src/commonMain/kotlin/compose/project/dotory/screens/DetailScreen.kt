package compose.project.dotory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.books
import dotory.composeapp.generated.resources.squirrel_read
import dotory.composeapp.generated.resources.story_background
import org.jetbrains.compose.resources.painterResource

@Composable
fun DetailScreen(navController: NavController, selectedItem: MutableState<Int>) {
    val storyText =
        "한경이는 여름날 숲 속을 산책하다, 말을 할 줄 아는 동물 친구들이 사는 신비로운 공간을 발견했어요. 사슴, 다람쥐, 올빼미, 여우가 다정하게 한경이를 맞이하며 함께 놀자고 초대했죠. 한경이는 동물들과 함께 숲을 탐험하며 즐겁게 놀았어요.\n\n" +
                "하지만 며칠 뒤, 숲에 이상한 일이 생기기 시작했어요. 공기가 탁해지고, 시냇물이 흐려진 거예요. 당황한 동물 친구들이 한경이에게 말했어요.\n\n" +
                "\"한경아, 숲이 아파하고 있어. 우리도 이 문제를 어떻게 해결해야 할지 모르겠어.\"\n\n" +
                "한경이는 고민에 잠기며 숲을 다시 건강하게 만들 방법을 떠올리기 시작했어요. 고민 끝에 동물 친구들과 숲을 살릴 방법을 생각해냈어요. \"우리가 함께 쓰레기를 치우고, 사람들이 다시는 이곳을 더럽히지 않도록 주의를 주자!\"라고 말했죠.\n\n" +
                "그렇게 한경이는 사람 친구들과 함께 숲 속 곳곳에 떨어진 쓰레기를 모으기 시작했어요. 쓰레기 봉투를 들고 여기저기 흩어진 플라스틱 병과 종이 조각을 주우며 하나씩 깨끗하게 정리해 나갔죠. 동물 친구들은 숲 속의 시냇가를 정화할 수 있도록 작은 물길을 만들거나, 깨끗한 물이 흐를 수 있도록 도왔어요.\n\n" +
                "며칠이 지나자, 숲은 다시 깨끗해지고 시냇물도 반짝이는 물결을 되찾았죠. 한경이와 친구들의 노력 덕분에 동물 친구들은 다시 평화로운 숲에서 행복하게 지낼 수 있게 되었어요.\n\n" +
                "마지막으로 한경이는 마을로 돌아가 사람들이 더 이상 숲에 쓰레기를 버리지 않도록 이야기했어요. 사람들은 한경이의 말을 듣고 환경을 지키는 데 함께 노력하기로 약속했답니다.\n\n" +
                "한경이는 깨끗해진 숲을 바라보며 미소 지었어요. 이제 숲 속 동물 친구들과의 모험도 계속 이어질 수 있겠죠."


    Box(
        modifier = Modifier
            .fillHeightOfParent(40.dp)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.books),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
            )
            Image(
                painter = painterResource(resource = Res.drawable.squirrel_read),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
            )
        }
    }

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .height(650.dp)
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.story_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        IconButton(
            onClick = { navController.navigate("BookShelfScreen") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
    Box(
        modifier = Modifier.padding(start = 25.dp, end = 25.dp) // 좌우 패딩
            .padding(top = 110.dp, bottom = 230.dp)
    ) { // 상단 10dp, 하단 100dp 패딩) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = storyText,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}