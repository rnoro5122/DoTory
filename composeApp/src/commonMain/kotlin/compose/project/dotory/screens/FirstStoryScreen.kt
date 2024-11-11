package compose.project.dotory.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.books
import dotory.composeapp.generated.resources.squirrel_read
import dotory.composeapp.generated.resources.story_background
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight

import kotlin.random.Random

@Composable
fun FirstStoryScreen(navController: NavController, selectedItem: MutableState<Int>) {
    val storyText =
        "한경이는 여름날 숲 속을 산책하다, 말을 할 줄 아는 동물 친구들이 사는 신비로운 공간을 발견했어요. 사슴, 다람쥐, 올빼미, 여우가 다정하게 한경이를 맞이하며 함께 놀자고 초대했죠. 한경이는 동물들과 함께 숲을 탐험하며 즐겁게 놀았어요.\n\n" +
                "하지만 며칠 뒤, 숲에 이상한 일이 생기기 시작했어요. 공기가 탁해지고, 시냇물이 흐려진 거예요. 당황한 동물 친구들이 한경이에게 말했어요.\n\n" +
                "\"한경아, 숲이 아파하고 있어. 우리도 이 문제를 어떻게 해결해야 할지 모르겠어.\""


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
                    onClick = { navController.navigate("PhotoUploadScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text("다음으로", color = MaterialTheme.colorScheme.background)
                }
            }
        }
    }
}