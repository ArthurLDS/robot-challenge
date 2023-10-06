package com.example.robot_game_play.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = darkColorScheme(
    primary = BackgroundColor,
    secondary = PlayerPink,
    tertiary = BackgroundColor,
    background = BackgroundColor,
    onPrimary = Color.White,
    onBackground = DarkPurple
)

@Composable
fun RobogameTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
