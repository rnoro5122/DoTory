package io.rnoro.dotory.presentation.screens.topicSelect

import Genre
import TopicSelectionViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
            navController = navController
        )
    }
}

@Composable
fun TopicSelectionContent(
    windowSizeClass: WindowSizeClass,
    viewModel: TopicSelectionViewModel,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLlmModeEnabled by viewModel.isLlmModeEnabled.collectAsState()

    Column {
        // Add LLM mode toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AI 동화 모드",
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = isLlmModeEnabled,
                onCheckedChange = { viewModel.toggleLlmMode() },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        when {
            uiState.isLoading -> LoadingScreen()
            uiState.errorMessage != null -> ErrorScreen(
                errorMessage = uiState.errorMessage!!,
                onRetry = viewModel::retryLoadingGenres
            )

            uiState.genres.isEmpty() -> ErrorScreen(
                errorMessage = "사용 가능한 장르가 없습니다.",
                onRetry = viewModel::retryLoadingGenres
            )

            else -> when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> CompactLayout(
                    genres = uiState.genres,
                    onGenreSelected = { genre ->
                        viewModel.selectGenre(navController, genre)
                    }
                )

                else -> ExpandedLayout(
                    genres = uiState.genres,
                    onGenreSelected = { genre ->
                        viewModel.selectGenre(navController, genre)
                    }
                )
            }
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
                text = "장르를 불러오는 중...",
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
    genres: List<Genre>,
    onGenreSelected: (Genre) -> Unit
) {
    if (genres.isEmpty()) {
        LoadingScreen()
        return
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(genres.first().imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
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
                    text = "원하는 장르를 선택해주세요",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

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
                    text = "추천 장르",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(genres) { genre ->
                        GenreCard(
                            genre = genre,
                            onGenreSelected = onGenreSelected,
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
    genres: List<Genre>,
    onGenreSelected: (Genre) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(genres.first().imageResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
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
                    text = "원하는 장르를 선택해주세요",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

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
                    text = "추천 장르",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(genres) { genre ->
                        GenreCard(
                            genre = genre,
                            onGenreSelected = onGenreSelected,
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
private fun GenreCard(
    genre: Genre,
    onGenreSelected: (Genre) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onGenreSelected(genre) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(genre.imageResource),
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
                    text = genre.displayName,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = genre.description,
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
                    genre.subTopics.take(3).forEach { subTopic ->
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
                    if (genre.subTopics.size > 3) {
                        Text(
                            text = "+${genre.subTopics.size - 3}",
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