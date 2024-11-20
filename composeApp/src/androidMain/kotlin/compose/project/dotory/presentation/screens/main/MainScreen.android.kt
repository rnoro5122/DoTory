package compose.project.dotory.presentation.screens.main

import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
actual fun WindowSizeWrapper(content: @Composable (WindowSizeClass) -> Unit) {
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(context as Activity)
    content(windowSizeClass)
}