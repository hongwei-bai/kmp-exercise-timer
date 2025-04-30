package com.example.kmpdemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleBubble(text: String, onClick: (String) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape) // Ensure shape applies to clickable area
            .background(color = MaterialTheme.colors.secondary)
            .clickable {
                onClick.invoke(text)
            }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}