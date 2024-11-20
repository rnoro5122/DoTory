package compose.project.dotory.presentation.screens.FairyTale

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FairyTaleScreen(
    viewModel: FairyTaleViewModel,
    topicId: String,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(topicId) {
        if (!viewModel.loadFairyTale(topicId)) {
            println("fairyTale not found for topicId: $topicId")  // 디버깅용
            onBackPressed()
            return@LaunchedEffect
        }
    }

    val fairyTale = viewModel.getCurrentFairyTale() ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(fairyTale.title) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 왼쪽 이미지 영역
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(fairyTale.pages[viewModel.currentPage].imageResource),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 오른쪽 텍스트 영역
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = fairyTale.pages[viewModel.currentPage].content,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )

                // 페이지 네비게이션 버튼
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (viewModel.canGoToPreviousPage()) {
                        Button(
                            onClick = { viewModel.goToPreviousPage() }
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "이전 페이지")
                            Spacer(Modifier.width(8.dp))
                            Text("이전")
                        }
                    }

                    if (viewModel.canGoToNextPage()) {
                        Button(
                            onClick = { viewModel.goToNextPage() }
                        ) {
                            Text("다음")
                            Spacer(Modifier.width(8.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "다음 페이지")
                        }
                    }
                }
            }
        }
    }
}