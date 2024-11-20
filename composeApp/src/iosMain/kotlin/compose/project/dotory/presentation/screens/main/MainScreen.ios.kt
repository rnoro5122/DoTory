// iosMain/kotlin/presentation/screens/main/WindowSizeWrapper.ios.kt
package compose.project.dotory.presentation.screens.main

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WindowSizeWrapper(content: @Composable (WindowSizeClass) -> Unit) {
    val bounds = UIScreen.mainScreen.bounds
    val width = bounds.size.width

    // iOS에서의 화면 크기에 따른 WindowSizeClass 결정
    val widthWindowSizeClass = when {
        width < 600 -> WindowWidthSizeClass.Compact
        width < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }

    val heightWindowSizeClass = when {
        bounds.size.height < 480 -> WindowHeightSizeClass.Compact
        bounds.size.height < 900 -> WindowHeightSizeClass.Medium
        else -> WindowHeightSizeClass.Expanded
    }

    content(WindowSizeClass(widthWindowSizeClass, heightWindowSizeClass))
}