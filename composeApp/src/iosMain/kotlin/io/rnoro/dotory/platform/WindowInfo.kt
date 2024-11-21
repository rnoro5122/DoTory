// iosMain/kotlin/compose/project/dotory/platform/WindowInfo.ios.kt
package io.rnoro.dotory.platform

import androidx.compose.runtime.Composable
import platform.UIKit.UIScreen

actual class WindowInfo(
    actual val screenWidthDp: Int,
    actual val screenHeightDp: Int
)

@Composable
actual fun rememberWindowInfo(): WindowInfo {
    val screen = UIScreen.mainScreen
    return WindowInfo(
        screenWidthDp = (screen.bounds.size.width * screen.scale).toInt(),
        screenHeightDp = (screen.bounds.size.height * screen.scale).toInt()
    )
}