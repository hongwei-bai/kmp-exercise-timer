package com.mikeapp.timer.ui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BigTimerTile(
    currentTime: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = currentTime,
        fontSize = 64.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}