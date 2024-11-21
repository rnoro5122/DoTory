package io.rnoro.dotory.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dotory.composeapp.generated.resources.Res
import dotory.composeapp.generated.resources.TmoneyRoundWindExtraBold
import dotory.composeapp.generated.resources.TmoneyRoundWindRegular


val tmoneyRoundWindFontFamily
    @Composable get() = FontFamily(
    Font(Res.font.TmoneyRoundWindRegular, weight = FontWeight.Normal),
    Font(Res.font.TmoneyRoundWindExtraBold, weight = FontWeight.ExtraBold)
)


val AppTypography
    @Composable get() = Typography().run {
    val fontFamily = tmoneyRoundWindFontFamily
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
