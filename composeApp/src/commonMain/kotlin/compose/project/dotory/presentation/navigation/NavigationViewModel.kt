package compose.project.dotory.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {
    private val _selectedItem = MutableStateFlow(1)
    val selectedItem = _selectedItem.asStateFlow()
    private var _navController: NavHostController? = null

    // 네비게이션 아이템 리스트를 ViewModel에서 관리
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

    fun setNavController(navController: NavHostController) {
        println("setNavController called with controller: ${navController.hashCode()}")
        _navController = navController
    }

    fun navigateToFairyTale(topicId: String) {
        println("NavigationViewModel: Preparing to navigate to fairy tale with topicId: $topicId")
        println("Current NavController: ${_navController?.hashCode()}")

        if (_navController == null) {
            println("WARNING: NavController is null!")
            return
        }

        val route = NavigationConfig.createFairyTaleRoute(topicId)
        println("NavigationViewModel: Generated route: $route")

        _navController?.navigate(route) {
            launchSingleTop = true
            popUpTo(NavigationConfig.TOPIC_SELECTION_SCREEN) {
                inclusive = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("NavigationViewModel cleared")
        _navController = null
    }
}

sealed class NavigationCommand {
    data class NavigateToFairyTale(val topicId: String) : NavigationCommand()
}

