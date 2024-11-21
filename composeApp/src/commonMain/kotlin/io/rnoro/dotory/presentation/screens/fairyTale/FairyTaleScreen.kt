package io.rnoro.dotory.presentation.screens.fairyTale

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
    navController: NavHostController
) {
    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(storyId) {
        if (viewModel.loadStory(storyId)) {
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
                    Button(
                        onClick = { navigationViewModel.navigateToActivityRecord(navController, storyId) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text("활동 기록하기")
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

                        if (viewModel.canGoToNextPage()) {
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
            }
        }
    }
}