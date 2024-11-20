package compose.project.dotory.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import compose.project.dotory.presentation.screens.BookShelfScreen
import compose.project.dotory.presentation.screens.FairyTale.FairyTaleScreen
import compose.project.dotory.presentation.screens.main.MainScreen
import compose.project.dotory.presentation.screens.topicSelect.TopicSelectionScreen

@Composable
fun AppNavigation(
    windowSizeClass: WindowSizeClass,
    viewModel: NavigationViewModel
) {
    val navController = rememberNavController()
    val isExpandedScreen = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    // navigationCommand 상태 변화 감지 및 처리
    LaunchedEffect(navController) {
        viewModel.setNavController(navController)
        println("NavController has been set")
    }

    SideEffect {
        println("Current NavController: ${navController.hashCode()}")
    }

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
                        navigationViewModel = viewModel
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
                    route = NavigationConfig.FAIRY_TALE_ROUTE,  // "FairyTaleScreen/{topicId}"와 동일
                    arguments = listOf(
                        navArgument("topicId") {
                            type = NavType.StringType
                            nullable = false
                        }
                    )
                ) { entry ->
                    val topicId = requireNotNull(entry.arguments?.getString("topicId")) {
                        "topicId parameter wasn't found. Please make sure it's passed in the navigation route."
                    }
                    println("FairyTaleScreen: Received topicId: $topicId")
                    FairyTaleScreen(
                        topicId = topicId,  // hardcoded "0" 대신 실제 topicId 사용
                        onBackPressed = { navController.popBackStack() }
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