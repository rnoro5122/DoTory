package io.rnoro.dotory.presentation.screens.fairyTale

import StoryBook
import StoryBookResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun FairyTaleScreen(
    viewModel: FairyTaleViewModel = viewModel(),
    navigationViewModel: NavigationViewModel = viewModel(),
    storyId: String,
    onBackPressed: () -> Unit,
    navController: NavHostController,
    isFromBookshelf: Boolean = false,
    isLlmMode: Boolean = false,
    topic: String? = null
) {
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(storyId) {
        if (viewModel.loadStory(storyId, isFromBookshelf, isLlmMode, topic)) {
            isLoaded = true
        } else {
            onBackPressed()
        }
    }

    if (!isLoaded) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (viewModel.isLlmMode) {
        LlmModeContent(
            displayedText = viewModel.displayedText,
            isLoading = viewModel.isLoading,
            isGenerationCompleted = viewModel.isGenerationCompleted,
            onBackPressed = onBackPressed,
            navController = navController,
            navigationViewModel = navigationViewModel,
            storyId = storyId,
            hasCompletedActivity = viewModel.hasCompletedActivity,
            hasCompletedStory = viewModel.hasCompletedStory,
            topic = topic // topic 전달
        )
    } else {
        RegularModeContent(
            viewModel = viewModel,
            navigationViewModel = navigationViewModel,
            navController = navController,
            onBackPressed = onBackPressed,
            isFromBookshelf = isFromBookshelf
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegularModeContent(
    viewModel: FairyTaleViewModel,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController,
    onBackPressed: () -> Unit,
    isFromBookshelf: Boolean
) {
    val currentPage = viewModel.getCurrentPage() ?: return
    val storyBook = viewModel.getCurrentStoryBook() ?: return
    var isLoading by remember { mutableStateOf(!isFromBookshelf) } // 책장에서 온 경우 초기값을 false로 설정

    // 책장에서 오지 않은 경우에만 로딩 효과 적용
    LaunchedEffect(Unit) {
        if (!isFromBookshelf) {
            kotlinx.coroutines.delay(4000)
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GPT 생성 동화") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    if (!isFromBookshelf && !viewModel.hasCompletedActivity) {
                        Button(
                            onClick = { navigationViewModel.navigateToActivityRecord(navController, storyBook.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text("활동 기록하기")
                        }
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "이야기를 불러오는 중...",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // 기존 콘텐츠는 동일하게 유지
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(currentPage.illustration),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentPage.content,
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth(),
                                softWrap = true,
                                lineHeight = 40.sp
                            )
                        }

                        NavigationButtons(
                            viewModel = viewModel,
                            navigationViewModel = navigationViewModel,
                            navController = navController,
                            storyBook = storyBook
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationButtons(
    viewModel: FairyTaleViewModel,
    navigationViewModel: NavigationViewModel,
    navController: NavHostController,
    storyBook: StoryBook
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        if (viewModel.canGoToPreviousPage()) {
            Button(
                onClick = { viewModel.goToPreviousPage() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "이전 페이지")
                Spacer(Modifier.width(8.dp))
                Text("이전")
            }
        }

        if (viewModel.isLastPageAndActivityCompleted()) {
            Button(
                onClick = {
                    viewModel.completeStory()
                    navigationViewModel.navigateToStoryComplete(navController, storyBook.id)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("이야기 완성하기")
            }
        } else if (viewModel.canGoToNextPage()) {
            Button(
                onClick = { viewModel.goToNextPage() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("다음")
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "다음 페이지")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LlmModeContent(
    displayedText: String,
    isLoading: Boolean,
    isGenerationCompleted: Boolean,
    onBackPressed: () -> Unit,
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    storyId: String,
    hasCompletedActivity: Boolean = false,
    hasCompletedStory: Boolean = false,
    topic: String? = null
) {
    // 한 번만 이미지를 선택하도록 state 관리
    val illustration = remember(topic, isGenerationCompleted) {
        if (isGenerationCompleted && topic != null) {
            StoryBookResources.allBooks
                .filter { book -> book.genre.id == topic }
                .flatMap { it.illustrations }
                .randomOrNull()
        } else null
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("On-Device-LLM 생성 동화") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    if (isGenerationCompleted && !hasCompletedActivity) {
                        Button(
                            onClick = {
                                navigationViewModel.navigateToActivityRecord(navController, storyId)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text("활동 기록하기")
                        }
                    }
                    if (isGenerationCompleted && hasCompletedActivity && !hasCompletedStory) {
                        Button(
                            onClick = {
                                navigationViewModel.navigateToStoryComplete(navController, storyId)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text("이야기 완성하기")
                        }
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "이야기 생성 중",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                // 생성이 완료되고 이미지가 있을 때만 이미지 표시
                if (illustration != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxSize(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Image(
                                painter = painterResource(illustration),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                // 텍스트 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(if (illustration != null) 2f else 1f)
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = displayedText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 30.sp,
                                lineHeight = 32.sp
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }
        }
    }
}