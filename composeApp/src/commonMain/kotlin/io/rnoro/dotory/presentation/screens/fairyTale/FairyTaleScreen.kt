package io.rnoro.dotory.presentation.screens.fairyTale

import StoryBook
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
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
            isGenerationCompleted = viewModel.isGenerationCompleted,  // 새로운 상태 전달
            onBackPressed = onBackPressed,
            navController = navController,
            navigationViewModel = navigationViewModel,
            storyId = storyId
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(storyBook.title) },
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
            )
        }
    ) { padding ->
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
    isGenerationCompleted: Boolean,  // 새로운 파라미터 추가
    onBackPressed: () -> Unit,
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    storyId: String,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI 동화") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    if (isGenerationCompleted) {  // 생성이 완료되면 버튼 표시
                        Button(
                            onClick = {
                                navigationViewModel.navigateToActivityRecord(
                                    navController,
                                    storyId
                                )
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
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
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
            } else {
                Text(
                    text = displayedText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 30.sp,
                        lineHeight = 40.sp
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            }
        }
    }
}