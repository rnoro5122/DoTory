package io.rnoro.dotory.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import io.rnoro.dotory.ui.theme.dark_background
import io.rnoro.dotory.ui.theme.dark_onPrimary
import io.rnoro.dotory.ui.theme.dark_onPrimaryContainer
import io.rnoro.dotory.ui.theme.dark_onSecondary
import io.rnoro.dotory.ui.theme.dark_onSecondaryContainer
import io.rnoro.dotory.ui.theme.dark_primary
import io.rnoro.dotory.ui.theme.dark_primaryContainer
import io.rnoro.dotory.ui.theme.dark_secondary
import io.rnoro.dotory.ui.theme.dark_secondaryContainer
import io.rnoro.dotory.ui.theme.light_background
import io.rnoro.dotory.ui.theme.light_onPrimary
import io.rnoro.dotory.ui.theme.light_onPrimaryContainer
import io.rnoro.dotory.ui.theme.light_onSecondary
import io.rnoro.dotory.ui.theme.light_onSecondaryContainer
import io.rnoro.dotory.ui.theme.light_primary
import io.rnoro.dotory.ui.theme.light_primaryContainer
import io.rnoro.dotory.ui.theme.light_secondary
import io.rnoro.dotory.ui.theme.light_secondaryContainer


private val LightColors = lightColorScheme(
    primary = light_primary,
    onPrimary = light_onPrimary,
    primaryContainer = light_primaryContainer,
    onPrimaryContainer = light_onPrimaryContainer,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    secondaryContainer = light_secondaryContainer,
    onSecondaryContainer = light_onSecondaryContainer,
    background = light_background
)

private val DarkColors = darkColorScheme(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    primaryContainer = dark_primaryContainer,
    onPrimaryContainer = dark_onPrimaryContainer,
    secondary = dark_secondary,
    onSecondary = dark_onSecondary,
    secondaryContainer = dark_secondaryContainer,
    onSecondaryContainer = dark_onSecondaryContainer,
    background = dark_background,

    )

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = AppTypography
    )
}