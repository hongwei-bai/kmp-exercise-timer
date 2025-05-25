package com.mikeapp.timer.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.mikeapp.timer.ui.util.formatMillisTo24hTime

@Composable
fun TimeRecordsColumn(
    timeRecords: List<Long>,
    modifier: Modifier = Modifier,
) {
    val maxAlpha = 1.0f
    val minAlpha = 0.3f
    val totalItems = timeRecords.size.coerceAtLeast(1)
    val scrollState = rememberScrollState()

    LaunchedEffect(timeRecords.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        timeRecords.forEachIndexed { index, record ->
            val alpha = if (timeRecords.size == 1) {
                maxAlpha // Only one item — full opacity
            } else {
                // Bottom item (latest) = 1.0f alpha, top = minAlpha
                val fadeIndex = index.toFloat() / (totalItems - 1)
                minAlpha + fadeIndex * (maxAlpha - minAlpha)
            }

            Text(
                text = formatMillisTo24hTime(record),
                fontSize = 24.sp,
                color = LocalContentColor.current.copy(alpha = alpha)
            )
        }
    }
}