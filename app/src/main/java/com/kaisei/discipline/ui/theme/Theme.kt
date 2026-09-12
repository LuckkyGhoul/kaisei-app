package com.kaisei.discipline.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightScheme = lightColorScheme(
    primary = DeepIndigo,
    onPrimary = WashiCream,
    secondary = VermilionRed,
    onSecondary = WashiCream,
    tertiary = MutedGold,
    background = WashiCream,
    onBackground = SumiBlack,
    surface = WashiCreamDark,
    onSurface = SumiBlack,
    error = Burgundy
)

private val DarkScheme = darkColorScheme(
    primary = MutedGold,
    onPrimary = SumiBlack,
    secondary = VermilionRed,
    onSecondary = WashiCream,
    tertiary = DeepIndigoLight,
    background = SumiBlack,
    onBackground = WashiCream,
    surface = SumiBlackSoft,
    onSurface = WashiCream,
    error = VermilionRed
)

@Composable
fun KaiseiTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkScheme else LightScheme
    MaterialTheme(
        colorScheme = colors,
        typography = KaiseiTypography,
        content = content
    )
}
