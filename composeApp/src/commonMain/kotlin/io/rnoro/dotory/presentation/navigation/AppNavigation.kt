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
import io.rnoro.dotory.presentation.screens.BookShelfScreen
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleScreen
import io.rnoro.dotory.presentation.screens.fairyTale.FairyTaleViewModel
import io.rnoro.dotory.presentation.screens.main.MainScreen
import io.rnoro.dotory.presentation.screens.topicSelect.TopicSelectionScreen
import androidx.lifecycle.viewmodel.compose.viewModel

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
                                // 이야기 생성 -> 홈 (아래로 밀려 내려옴)
                                slideInVertically(
                                    initialOffsetY = { -it },
                                    animationSpec = tween(300)
                                )
                            }
                            NavigationConfig.BOOKSHELF_SCREEN -> {
                                // 책장 -> 홈 (위로 밀려 올라옴)
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
                                // 홈 -> 이야기 생성 (아래로 밀려남)
                                slideOutVertically(
                                    targetOffsetY = { -it },
                                    animationSpec = tween(300)
                                )
                            }
                            NavigationConfig.BOOKSHELF_SCREEN -> {
                                // 홈 -> 책장 (위로 밀려남)
                                slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(300)
                                )
                            }
                            else -> null
                        }
                    }
                ) {
                    MainScreen()
                }

                composable(
                    route = NavigationConfig.TOPIC_SELECTION_SCREEN,
                    enterTransition = {
                        // 이야기 생성 화면이 아래에서 위로 밀고 올라옴
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(300)
                        )
                    },
                    exitTransition = {
                        // 이야기 생성 화면이 위로 밀려 올라가며 사라짐
                        slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(300)
                        )
                    }
                ) {
                    TopicSelectionScreen(
                        navigationViewModel = viewModel,
                        navController = navController  // NavController 직접 전달
                    )
                }

                composable(
                    route = NavigationConfig.BOOKSHELF_SCREEN,
                    enterTransition = {
                        // 책장 화면이 위에서 아래로 밀고 내려옴
                        slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(300)
                        )
                    },
                    exitTransition = {
                        // 책장 화면이 아래로 밀려 내려가며 사라짐
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(300)
                        )
                    }
                ) {
                    BookShelfScreen()
                }

                composable(
                    route = NavigationConfig.FAIRY_TALE_ROUTE,
                    arguments = listOf(
                        navArgument("topicId") {
                            type = NavType.StringType
                            nullable = false
                        }
                    )
                ) { entry ->
                    val topicId = requireNotNull(entry.arguments?.getString("topicId"))
                    val fairyTaleViewModel: FairyTaleViewModel = viewModel()

                    FairyTaleScreen(
                        viewModel = fairyTaleViewModel,
                        storyId = topicId,
                        onBackPressed = { navController.popBackStack() },
                        navController = navController
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