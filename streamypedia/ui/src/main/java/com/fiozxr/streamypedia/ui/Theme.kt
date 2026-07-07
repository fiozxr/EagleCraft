package com.fiozxr.streamypedia.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val StreamyDarkColors = darkColorScheme(
    primary = Color(0xFF0F766E), // Teal
    secondary = Color(0xFF4C1D95), // Violet
    background = Color(0xFF1E1B4B), // Deep Indigo
    surface = Color(0x0DFFFFFF), // Glass base
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun StreamypediaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StreamyDarkColors,
        content = content
    )
}
