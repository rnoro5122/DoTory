package compose.project.dotory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.bookshelf_icon
import dotory.composeapp.generated.resources.tree_icon
import dotory.composeapp.generated.resources.write_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(
    selectedItem: MutableState<Int>,
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            modifier = Modifier
                .height(100.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(30.dp)),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(Res.drawable.bookshelf_icon),
                        contentDescription = "bookshelf_icon"
                    )
                },
                label = {
                    Text(
                        "책장",
                        fontSize = 20.sp
                    )
                },
                selected = selectedItem.value == 0,
                onClick = {
                    selectedItem.value = 0
                    navController.navigate("BookShelfScreen") {
                        popUpTo("MainScreen") { inclusive = true }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(Res.drawable.tree_icon),
                        contentDescription = "홈"
                    )
                },
                label = {
                    Text(
                        "홈",
                        fontSize = 20.sp
                    )
                },
                selected = selectedItem.value == 1,
                onClick = {
                    selectedItem.value = 1
                    navController.navigate("MainScreen") {
                        popUpTo("MainScreen") { inclusive = true }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(Res.drawable.write_icon),
                        contentDescription = ""
                    )
                },
                label = {
                    Text(
                        "이야기 생성",
                        fontSize = 20.sp
                    )
                },
                selected = selectedItem.value == 2,
                onClick = {
                    selectedItem.value = 2
                    navController.navigate("SelectThemeScreen") {
                        popUpTo("MainScreen")
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}