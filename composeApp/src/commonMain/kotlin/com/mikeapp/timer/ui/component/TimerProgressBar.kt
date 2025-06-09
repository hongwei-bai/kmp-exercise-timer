package com.mikeapp.timer.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun TimerProgressBar(
    totalDuration: Long,
    currentTime: Long,
    totalMinutes: Int,
    dividerMinute: Int,
    isDividerMuted: Boolean,
    isEndMuted: Boolean,
    onDividerBellClicked: () -> Unit,
    onAlarmBellClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = (currentTime.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(progress)

    val trackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    val textColor = MaterialTheme.colors.onSurface
    val dividerTime = dividerMinute * 60 * 1000L
    val totalTime = totalMinutes * 60 * 1000L

    val progressColor: Color
    var dividerColor: Color
    when {
        currentTime < dividerTime -> {
            progressColor = MaterialTheme.colors.primary
            dividerColor = MaterialTheme.colors.primary.copy(alpha = 0.3f)
        }

        currentTime <= totalTime -> {
            progressColor = MaterialTheme.colors.secondary
            dividerColor = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.3f)
        }

        else -> {
            progressColor = MaterialTheme.colors.secondaryVariant
            dividerColor = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
        }
    }
    if (isDividerMuted) {
        dividerColor = MaterialTheme.colors.error.copy(alpha = 0.3f)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        val barWidth = constraints.maxWidth.toFloat()
        val dividerPositionPx = barWidth * dividerMinute / totalMinutes
        val dividerPositionDp = with(LocalDensity.current) { dividerPositionPx.toDp() }
        val labelDistanceDp = with(LocalDensity.current) { (barWidth - dividerPositionPx).toDp() }

        val closeThreshold = 56.dp
        val tooCloseThreshold = 32.dp

        val showCompact = labelDistanceDp < closeThreshold && labelDistanceDp >= tooCloseThreshold
        val showAbove = labelDistanceDp < tooCloseThreshold

        Column {
            if (showAbove) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    Text(
                        text = formatDurationFlexible(dividerMinute),
                        color = if (isDividerMuted) MaterialTheme.colors.error else textColor,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .absoluteOffset(x = dividerPositionDp - 16.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(trackColor, RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(24.dp)
                        .background(progressColor, RoundedCornerShape(4.dp))
                )
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val dividerX = size.width * dividerMinute / totalMinutes
                    drawRect(
                        color = dividerColor,
                        topLeft = Offset(dividerX, 0f),
                        size = Size(2.dp.toPx(), size.height)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            ) {
                IconButton(
                    onClick = {
                        onDividerBellClicked.invoke()
                    },
                    modifier = Modifier
                        .absoluteOffset(x = dividerPositionDp - 9.5.dp)
                        .size(20.dp)
                ) {
                    Icon(
                        imageVector = if (isDividerMuted) Icons.Filled.NotificationsOff else Icons.Filled.Notifications,
                        contentDescription = "Divider Alarm",
                        tint = if (isDividerMuted) MaterialTheme.colors.error else progressColor
                    )
                }

                IconButton(
                    onClick = {
                        onAlarmBellClicked.invoke()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(20.dp)
                ) {
                    Icon(
                        imageVector = if (isEndMuted) Icons.Filled.NotificationsOff else Icons.Filled.Notifications,
                        contentDescription = "End Alarm",
                        tint = if (isEndMuted) MaterialTheme.colors.error else MaterialTheme.colors.secondary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
            ) {
                if (!showAbove) {
                    Text(
                        text = if (showCompact) formatShortUnit(dividerMinute) else formatDurationFlexible(dividerMinute),
                        color = if (isDividerMuted) MaterialTheme.colors.error else textColor,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .absoluteOffset(x = dividerPositionDp - if (showCompact) 10.dp else 16.dp)
                    )
                }

                Text(
                    text = if (showCompact) formatShortUnit(totalMinutes) else formatDurationFlexible(totalMinutes),
                    color = textColor,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

fun formatDurationFlexible(totalMinutes: Int): String {
    val parts = mutableListOf<String>()
    val days = totalMinutes / (24 * 60)
    val remainingMinutes = totalMinutes % (24 * 60)
    val fractionalHours = remainingMinutes / 60.0

    if (days > 0) {
        parts.add("${days}d")
    }

    if (fractionalHours >= 1) {
        val rounded = (fractionalHours * 10).roundToInt() / 10.0
        parts.add("$rounded ${if (rounded == 1.0) "hour" else "hours"}")
    } else if (totalMinutes > 0 || parts.isEmpty()) {
        parts.add("$totalMinutes ${if (totalMinutes == 1) "min" else "mins"}")
    }

    return parts.joinToString(" ")
}

fun formatShortUnit(totalMinutes: Int): String {
    val h = totalMinutes / 60.0
    return if (h >= 1) {
        "${(h * 10).roundToInt() / 10.0}h"
    } else {
        "${totalMinutes}m"
    }
}
