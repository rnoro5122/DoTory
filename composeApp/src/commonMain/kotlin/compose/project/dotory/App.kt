package compose.project.dotory

import androidx.compose.runtime.Composable
import compose.project.dotory.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        PythonLauncher {
            MainView()
        }
    }
}