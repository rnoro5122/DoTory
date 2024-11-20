package compose.project.dotory.platform

import androidx.compose.runtime.Composable

expect class WindowInfo {
    val screenWidthDp: Int
    val screenHeightDp: Int
}

@Composable
expect fun rememberWindowInfo(): WindowInfo