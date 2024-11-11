package compose.project.dotory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import compose.project.dotory.screens.BookShelfScreen
import compose.project.dotory.screens.DetailScreen
import compose.project.dotory.screens.FinishScreen
import compose.project.dotory.screens.FirstStoryScreen
import compose.project.dotory.screens.MainScreen
import compose.project.dotory.screens.SelectThemeScreen
import compose.project.dotory.screens.PhotoUploadScreen
import compose.project.dotory.screens.SecondStoryScreen

@Composable
fun MainView() {
    val selectedItem = remember { mutableStateOf(1) }
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
//            .systemBarsPadding()
    ) {
        NavHost(
            navController = navController,
            startDestination = "MainScreen"
        ) {
            composable("MainScreen") {
                MainScreen(navController, selectedItem)
            }
            composable("SelectThemeScreen") {
                SelectThemeScreen(navController, selectedItem)
            }
            composable("FirstStoryScreen") {
                FirstStoryScreen(navController, selectedItem)
            }
            composable("PhotoUploadScreen") {
                PhotoUploadScreen(navController)
            }
            composable("SecondStoryScreen") {
                SecondStoryScreen(navController, selectedItem)
            }
            composable("BookShelfScreen") {
                BookShelfScreen(navController, selectedItem)
            }
            composable("FinishScreen") {
                FinishScreen(navController, selectedItem)
            }
            composable("DetailScreen") {
                DetailScreen(navController, selectedItem)
            }
        }

        // BottomNavigationBar is enaabled only when the current screen is MainScreen or SelectThemeScreen
        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        if (currentBackStackEntry?.destination?.route == "MainScreen" ||
            currentBackStackEntry?.destination?.route == "SelectThemeScreen" ||
            currentBackStackEntry?.destination?.route == "BookShelfScreen"
        ) {
            BottomNavigationBar(selectedItem, navController)
        }
    }
}