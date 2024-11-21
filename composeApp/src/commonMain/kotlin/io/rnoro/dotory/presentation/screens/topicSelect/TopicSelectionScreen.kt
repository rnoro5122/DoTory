package io.rnoro.dotory.presentation.screens.topicSelect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.rnoro.dotory.domain.models.Topic
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
expect fun WindowSizeWrapper(content: @Composable (WindowSizeClass) -> Unit)

@Composable
fun TopicSelectionScreen(
    navigationViewModel: NavigationViewModel,
    navController: NavHostController,
) {
    WindowSizeWrapper { windowSizeClass ->
        val viewModel = remember { TopicSelectionViewModel(navigationViewModel) }

        TopicSelectionContent(
            windowSizeClass = windowSizeClass,
            viewModel = viewModel,
            navController = navController  // NavController 전달
        )
    }
}


@Composable
fun TopicSelectionContent(
    windowSizeClass: WindowSizeClass,
    viewModel: TopicSelectionViewModel,
    navController: NavHostController  // NavController 파라미터 추가
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingScreen()
        uiState.errorMessage != null -> ErrorScreen(
            errorMessage = uiState.errorMessage!!,
            onRetry = viewModel::retryLoadingTopics
        )
        uiState.topics.isEmpty() -> ErrorScreen(
            errorMessage = "사용 가능한 주제가 없습니다.",
            onRetry = viewModel::retryLoadingTopics
        )
        else -> when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> CompactLayout(
                topics = uiState.topics,
                onTopicSelected = { topic ->
                    viewModel.selectTopic(navController, topic)
                }
            )
            else -> ExpandedLayout(
                topics = uiState.topics,
                onTopicSelected = { topic ->
                    viewModel.selectTopic(navController, topic)
                }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "주제를 불러오는 중...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("다시 시도하기")
            }
        }
    }
}

@Composable
private fun CompactLayout(
    topics: List<Topic>,
    onTopicSelected: (Topic) -> Unit
) {
    if (topics.isEmpty()) {
        LoadingScreen()
        return
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 상단 이미지
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(topics.firstOrNull()?.imageResource ?: topics[0].imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // 어두운 오버레이 추가
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            // 텍스트 오버레이
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = "어떤 이야기를 만들까요?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "원하는 주제를 선택해주세요",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // 하단 주제 선택 영역
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "추천 주제",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(topics) { topic ->
                        TopicCard(
                            topic = topic,
                            onTopicSelected = onTopicSelected,
                            modifier = Modifier.width(280.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandedLayout(
    topics: List<Topic>,
    onTopicSelected: (Topic) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // topics가 비어있으면 로딩 표시
        if (topics.isEmpty()) {
            LoadingScreen()
            return
        }

        //왼쪽 이미지
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(topics.firstOrNull()?.imageResource ?: topics[0].imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // 어두운 오버레이
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            // 텍스트 오버레이
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
            ) {
                Text(
                    text = "어떤 이야기를 만들까요?",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "원하는 주제를 선택해주세요",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // 오른쪽 주제 선택 영역
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "추천 주제",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(topics) { topic ->
                        TopicCard(
                            topic = topic,
                            onTopicSelected = onTopicSelected,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun TopicCard(
    topic: Topic,
    onTopicSelected: (Topic) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onTopicSelected(topic) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(topic.imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = topic.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    topic.subTopics.take(3).forEach { subTopic ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = subTopic,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    if (topic.subTopics.size > 3) {
                        Text(
                            text = "+${topic.subTopics.size - 3}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}