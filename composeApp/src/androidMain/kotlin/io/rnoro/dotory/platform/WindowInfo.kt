// androidMain/kotlin/compose/project/dotory/platform/WindowInfo.android.kt
package io.rnoro.dotory.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

actual class WindowInfo(
    actual val screenWidthDp: Int,
    actual val screenHeightDp: Int
)

@Composable
actual fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthDp = configuration.screenWidthDp,
        screenHeightDp = configuration.screenHeightDp
    )
}