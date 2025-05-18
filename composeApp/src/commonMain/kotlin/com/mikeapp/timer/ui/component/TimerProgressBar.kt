package com.mikeapp.timer.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerProgressBar(
    totalDuration: Long,
    currentTime: Long,
    totalMinutes: Int,
    dividerMinute: Int,
    modifier: Modifier = Modifier,
    onWarning: (Int) -> Unit = {},
    onComplete: (Int) -> Unit = {}
) {
    var lastWarnedMinutes by rememberSaveable { mutableIntStateOf(-1) }
    var lastCompleteMinutes by rememberSaveable { mutableIntStateOf(-1) }

    val progress = (currentTime.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(progress)

    val trackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    val dividerColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
    val textColor = MaterialTheme.colors.onSurface
    val dividerTime = dividerMinute * 60 * 1000L
    val progressColor = if (currentTime < dividerTime) {
        MaterialTheme.colors.primary // Green
    } else {
        MaterialTheme.colors.error // Red
    }
    if (dividerMinute > lastWarnedMinutes && currentTime >= dividerTime) {
        lastWarnedMinutes = dividerMinute
        onWarning.invoke(lastWarnedMinutes)
    }

    val completeTime = totalMinutes * 60 * 1000L
    if (totalMinutes > lastCompleteMinutes && currentTime >= completeTime) {
        lastCompleteMinutes = totalMinutes
        onComplete.invoke(lastCompleteMinutes)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        val barWidth = constraints.maxWidth.toFloat()
        val dividerPositionPx = barWidth * dividerMinute / totalMinutes
        val dividerPositionDp = with(LocalDensity.current) { dividerPositionPx.toDp() }

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                // Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(trackColor, RoundedCornerShape(4.dp))
                )
                // Progress
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(24.dp)
                        .background(progressColor, RoundedCornerShape(4.dp))
                )
                // Divider
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val dividerX = size.width * dividerMinute / totalMinutes
                    drawRect(
                        color = dividerColor,
                        topLeft = Offset(dividerX, 0f),
                        size = Size(2.dp.toPx(), size.height)
                    )
                }
            }

            // Labels
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "$dividerMinute min",
                    color = textColor,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .absoluteOffset(x = dividerPositionDp - 16.dp)
                )
                Text(
                    text = "$totalMinutes min",
                    color = textColor,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}
