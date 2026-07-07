package com.fiozxr.streamypedia.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x0DFFFFFF)) // rgba(255, 255, 255, 0.05)
            .border(1.dp, Color(0x1EFFFFFF), RoundedCornerShape(20.dp)) // rgba(255, 255, 255, 0.12)
            .padding(16.dp)
            // Note: blur modifier and exact dropshadow omitted for basic placeholder
    ) {
        content()
    }
}
