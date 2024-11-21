package io.rnoro.dotory.presentation.navigation

import ActivityRecordScreen
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleScreen
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleViewModel
import io.rnoro.dotory.presentation.screens.main.MainScreen
import io.rnoro.dotory.presentation.screens.topicSelect.TopicSelectionScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import io.rnoro.dotory.presentation.screens.bookShelf.BookShelfScreen
import io.rnoro.dotory.presentation.screens.storyComplete.StoryCompleteScreen

@Composable
fun AppNavigation(
    windowSizeClass: WindowSizeClass,
    viewModel: NavigationViewModel
) {
    val navController = rememberNavController()
    val isExpandedScreen = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    Row(modifier = Modifier.fillMaxSize()) {
        if (isExpandedScreen) {
            NavigationContent(
                viewModel = viewModel,
                navController = navController,
                isVertical = true
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = navController,
                startDestination = NavigationConfig.MAIN_SCREEN
            ) {
                composable(
                    route = NavigationConfig.MAIN_SCREEN,
                    enterTransition = {
                        when (initialState.destination.route) {
                            NavigationConfig.TOPIC_SELECTION_SCREEN -> {
                                // TopicSelection -> Main (Main이 제자리에서 나타남)
                                slideInVertically(
                                    initialOffsetY = { -it },
                                    animationSpec = tween(300)
                                )
                            }
                            NavigationConfig.BOOKSHELF_SCREEN -> {
                                // Bookshelf -> Main (Main이 아래에서 위로 밀고 올라옴)
                                slideInVertically(
                                    initialOffsetY = { it },
                                    animationSpec = tween(300)
                                )
                            }
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            NavigationConfig.TOPIC_SELECTION_SCREEN -> {
                                // Main -> TopicSelection (Main이 위로 밀려 올라감)
                                slideOutVertically(
                                    targetOffsetY = { -it },
                                    animationSpec = tween(300)
                                )
                            }
                            NavigationConfig.BOOKSHELF_SCREEN -> {
                                // Main -> Bookshelf (Main이 아래로 밀려남)
                                slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(300)
                                )
                            }
                            else -> null
                        }
                    }
                ) {
                    MainScreen(navController = navController, navigationViewModel = viewModel)
                }

                composable(
                    route = NavigationConfig.TOPIC_SELECTION_SCREEN,
                    enterTransition = {
                        // Main -> TopicSelection (TopicSelection이 아래에서 위로 밀고 올라옴)
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(300)
                        )
                    },
                    exitTransition = {
                        // TopicSelection -> Main (TopicSelection이 아래로 밀려 내려감)
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(300)
                        )
                    }
                ) {
                    TopicSelectionScreen(
                        navigationViewModel = viewModel,
                        navController = navController
                    )
                }

                composable(
                    route = NavigationConfig.BOOKSHELF_SCREEN,
                    enterTransition = {
                        // Main -> Bookshelf (Bookshelf가 위에서 아래로 밀고 내려옴)
                        slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(300)
                        )
                    },
                    exitTransition = {
                        // Bookshelf -> Main (Bookshelf가 위로 밀려나감)
                        slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(300)
                        )
                    }
                ) {
                    BookShelfScreen(navController = navController, navigationViewModel = viewModel)
                }

                composable(
                    route = NavigationConfig.FAIRY_TALE_ROUTE,
                    arguments = listOf(
                        navArgument("topicId") {
                            type = NavType.StringType
                            nullable = false
                        },
                        navArgument("isFromBookshelf") {
                            type = NavType.BoolType
                            defaultValue = false
                        }
                    )
                ) { entry ->
                    val topicId = requireNotNull(entry.arguments?.getString("topicId"))
                    val isFromBookshelf = entry.arguments?.getBoolean("isFromBookshelf") ?: false
                    val fairyTaleViewModel: FairyTaleViewModel = viewModel()

                    FairyTaleScreen(
                        viewModel = fairyTaleViewModel,
                        storyId = topicId,
                        onBackPressed = { navController.popBackStack() },
                        navController = navController,
                        isFromBookshelf = isFromBookshelf
                    )
                }

                composable(
                    route = NavigationConfig.ACTIVITY_RECORD_ROUTE,  // 정확히 동일한 라우트 사용
                    arguments = listOf(
                        navArgument("storyId") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val storyId = requireNotNull(backStackEntry.arguments?.getString("storyId"))
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(NavigationConfig.FAIRY_TALE_ROUTE)
                    }
                    val fairyTaleViewModel: FairyTaleViewModel = viewModel(parentEntry)

                    ActivityRecordScreen(
                        viewModel = fairyTaleViewModel,
                        navController = navController
                    )
                }

                composable(
                    route = NavigationConfig.STORY_COMPLETE_ROUTE,
                    arguments = listOf(
                        navArgument("storyId") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val storyId = requireNotNull(backStackEntry.arguments?.getString("storyId"))
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(NavigationConfig.FAIRY_TALE_ROUTE)
                    }
                    val fairyTaleViewModel: FairyTaleViewModel = viewModel(parentEntry)

                    StoryCompleteScreen(
                        viewModel = fairyTaleViewModel,
                        navigationViewModel = viewModel,  // NavigationViewModel 전달
                        navController = navController,
                        storyId = storyId
                    )
                }
            }

            // 작은 화면에서만 하단 네비게이션 표시
            if (!isExpandedScreen) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.BottomCenter
                ) {
                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                    if (currentRoute in NavigationConfig.BOTTOM_NAV_SCREENS) {
                        NavigationContent(
                            viewModel = viewModel,
                            navController = navController,
                            isVertical = false
                        )
                    }
                }
            }
        }
    }
}