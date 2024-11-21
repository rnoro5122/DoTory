package io.rnoro.dotory

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


var moduleNamePreset: String = "main"
    set(name) {
        if (field.isNotEmpty()) {
            throw IllegalStateException("Python Module Name Preset can be set only once.")
        }
        field = name
    }


@Composable
expect fun PythonLauncher(
    content: @Composable () -> Unit
)


@Composable
expect fun PythonWidget(
    composableName: String,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    moduleName: String = moduleNamePreset,
    content: @Composable (args: Array<Any>) -> Unit = {}
)


@Composable
expect fun PythonAppView(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null
)
