package com.example.taskmanager.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) {
        AppColors.darkColors
    } else {
        AppColors.lightColors
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}
