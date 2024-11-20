package compose.project.dotory.presentation.navigation

import org.jetbrains.compose.resources.DrawableResource

data class NavigationItem(
    val icon: DrawableResource,
    val label: String,
    val route: String
)