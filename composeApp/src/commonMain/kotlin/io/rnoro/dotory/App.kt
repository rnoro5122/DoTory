package io.rnoro.dotory

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.rnoro.dotory.presentation.navigation.AppNavigation
import io.rnoro.dotory.presentation.screens.main.WindowSizeWrapper
import io.rnoro.dotory.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        PythonLauncher {
            WindowSizeWrapper { windowSizeClass ->
                Surface(
                    modifier = Modifier.fillMaxSize().systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(windowSizeClass, viewModel())
                }
            }
        }
    }
}