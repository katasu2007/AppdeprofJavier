package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val RachaDarkColorScheme = darkColorScheme(
    primary = NeonOrangePrimary,
    onPrimary = TextWhiteHighContrast,
    primaryContainer = NeonDarkSurfaceVariant,
    onPrimaryContainer = NeonAmberGlow,
    secondary = NeonCyan,
    onSecondary = NeonDarkBackground,
    secondaryContainer = NeonDarkSurfaceVariant,
    onSecondaryContainer = NeonCyan,
    tertiary = NeonLime,
    onTertiary = NeonDarkBackground,
    background = NeonDarkBackground,
    onBackground = TextWhiteHighContrast,
    surface = NeonDarkSurface,
    onSurface = TextWhiteHighContrast,
    surfaceVariant = NeonDarkSurfaceVariant,
    onSurfaceVariant = TextLightSlate,
    outline = NeonDarkBorder,
    outlineVariant = NeonDarkBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    // Always use the customized Neon Dark Theme as strictly requested
    MaterialTheme(
        colorScheme = RachaDarkColorScheme,
        typography = Typography,
        content = content
    )
}
