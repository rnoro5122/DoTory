package compose.project.dotory.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationContent(
    viewModel: NavigationViewModel,
    navController: NavHostController,
    isVertical: Boolean = false
) {
    val selectedItem by viewModel.selectedItem.collectAsState()

    if (isVertical) {
        NavigationRail(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 8.dp)
                .clip(RoundedCornerShape(30.dp)),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
            ) {
                viewModel.navigationItems.forEachIndexed { index, item ->
                    NavigationRailItem(
                        icon = {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(resource = item.icon),
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label, fontSize = 16.sp) },
                        selected = selectedItem == index,
                        onClick = {
                            viewModel.navigate(navController, item.route, index)
                        },
                        colors = NavigationRailItemDefaults.colors(
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
    } else {
        NavigationBar(
            modifier = Modifier
                .height(100.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(30.dp)),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            viewModel.navigationItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(item.icon),
                            contentDescription = item.label
                        )
                    },
                    label = { Text(item.label, fontSize = 20.sp) },
                    selected = selectedItem == index,
                    onClick = {
                        viewModel.navigate(navController, item.route, index)
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
}