package compose.project.dotory.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.books
import dotory.composeapp.generated.resources.squirrel_read
import dotory.composeapp.generated.resources.story_background
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

@Composable
fun SecondStoryScreen(navController: NavController, selectedItem: MutableState<Int>) {
    val storyText =
        "한경이는 고민 끝에 동물 친구들과 숲을 살릴 방법을 생각해냈어요. \"우리가 함께 쓰레기를 치우고, 사람들이 다시는 이곳을 더럽히지 않도록 주의를 주자!\"라고 말했죠.\n\n" +
                "그렇게 한경이는 친구들과 함께 숲 속 곳곳에 떨어진 쓰레기를 모으기 시작했어요. 쓰레기 봉투를 들고 여기저기 흩어진 플라스틱 병과 종이 조각을 주우며 하나씩 깨끗하게 정리해 나갔죠. 동물 친구들은 숲 속의 시냇가를 정화할 수 있도록 도왔어요.\n\n" +
                "며칠이 지나자, 숲은 다시 깨끗해지고 시냇물도 반짝이는 물결을 되찾았죠. 한경이와 친구들의 노력 덕분에 동물 친구들은 다시 평화로운 숲에서 행복하게 지낼 수 있게 되었어요.\n\n" +
                "마지막으로 한경이는 마을로 돌아가 사람들이 더 이상 숲에 쓰레기를 버리지 않도록 이야기했어요. 사람들은 한경이의 말을 듣고 환경을 지키는 데 함께 노력하기로 약속했답니다.\n\n" +
                "한경이는 깨끗해진 숲을 바라보며 미소 지었어요. 이제 숲 속 동물 친구들과의 모험도 계속 이어질 수 있겠죠."


    val words = storyText.split(" ") // 텍스트를 단어별로 분리
    var displayedText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var showNextButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000) // 처음 3초 동안 로딩 프로그레스 표시
        isLoading = false
        words.forEach { word ->
            val randomDelay = Random.nextLong(100, 300) // 100ms ~ 300ms 사이의 불규칙적 지연
            delay(randomDelay)
            displayedText += "$word " // 한 단어씩 추가
        }
        showNextButton = true // 모든 단어가 표시된 후 버튼 표시
    }

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
                .align(Alignment.Center),
        )

        if (isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp)) // 인디케이터와 텍스트 사이에 간격 추가
                Text(
                    text = "이야기 생성 중",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }
        } else {
            // 텍스트 애니메이션
            Text(
                text = displayedText,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .align(Alignment.Center)
            )

            // 모든 텍스트가 표시된 후 버튼 페이드인
            AnimatedVisibility(
                visible = showNextButton,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate("FinishScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text("동화 완성하기", color = MaterialTheme.colorScheme.background)
                }
            }
        }
    }
}