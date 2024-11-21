package io.rnoro.dotory.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import io.rnoro.dotory.presentation.navigation.NavigationConfig.createActivityRecordRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {
    private val _selectedItem = MutableStateFlow(1)
    val selectedItem = _selectedItem.asStateFlow()

    val navigationItems = listOf(
        NavigationItem(
            icon = NavigationConfig.ICON_BOOKSHELF,
            label = NavigationConfig.LABEL_BOOKSHELF,
            route = NavigationConfig.BOOKSHELF_SCREEN
        ),
        NavigationItem(
            icon = NavigationConfig.ICON_HOME,
            label = NavigationConfig.LABEL_HOME,
            route = NavigationConfig.MAIN_SCREEN
        ),
        NavigationItem(
            icon = NavigationConfig.ICON_CREATE_STORY,
            label = NavigationConfig.LABEL_CREATE_STORY,
            route = NavigationConfig.TOPIC_SELECTION_SCREEN
        )
    )

    fun navigate(navController: NavHostController, route: String, index: Int) {
        _selectedItem.value = index
        navController.navigate(route) {
            launchSingleTop = true
            when (route) {
                "MainScreen", "BookShelfScreen" -> {
                    popUpTo("MainScreen") { inclusive = true }
                }
            }
        }
    }

    fun navigateToFairyTale(
        navController: NavHostController,
        storyId: String,
        isFromBookshelf: Boolean = false,
        isLlmMode: Boolean = false,
        topic: String? = null
    ) {
        try {
            val route = NavigationConfig.createFairyTaleRoute(
                topicId = storyId,
                isFromBookshelf = isFromBookshelf,
                isLlmMode = isLlmMode,
                topic = topic
            )
            navController.navigate(route)
        } catch (e: Exception) {
            println("❌ 네비게이션 실패: ${e.message}")
            e.printStackTrace()
        }
    }

    fun navigateToActivityRecord(navController: NavHostController, storyId: String) {
        try {
            val route = NavigationConfig.createActivityRecordRoute(storyId)
            navController.navigate(route)
        } catch (e: Exception) {
            println("❌ 활동 기록 네비게이션 실패: ${e.message}")
            e.printStackTrace()
        }
    }

    fun navigateToStoryComplete(navController: NavHostController, storyId: String) {
        try {
            val route = NavigationConfig.createStoryCompleteRoute(storyId)
            navController.navigate(route)
        } catch (e: Exception) {
            println("❌ 이야기 완성 네비게이션 실패: ${e.message}")
            e.printStackTrace()
        }
    }

    fun navigateToHome(navController: NavHostController) {
        _selectedItem.value = 1  // 홈 아이템 선택 (인덱스가 1)
        navController.navigate(NavigationConfig.MAIN_SCREEN) {
            popUpTo(NavigationConfig.MAIN_SCREEN) {  // 네비게이션 스택 전체 제거
                inclusive = true
            }
            launchSingleTop = true  // 중복 방지
        }
    }
}

