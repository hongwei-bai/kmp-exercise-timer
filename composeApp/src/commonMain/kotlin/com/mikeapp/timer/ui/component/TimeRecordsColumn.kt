package com.mikeapp.timer.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikeapp.timer.ui.util.formatMillisTo24hTime
import com.mikeapp.timer.ui.util.getAmPmFromTimeMs

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
                maxAlpha
            } else {
                val fadeIndex = index.toFloat() / (totalItems - 1)
                minAlpha + fadeIndex * (maxAlpha - minAlpha)
            }

            val period = getAmPmFromTimeMs(record)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = formatMillisTo24hTime(record),
                        fontSize = 24.sp,
                        color = LocalContentColor.current.copy(alpha = alpha)
                    )
                    Text(
                        text = period,
                        fontSize = 10.sp,
                        color = LocalContentColor.current.copy(alpha = alpha),
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .align(if (period == "AM") Alignment.Top else Alignment.Bottom)
                            .absoluteOffset(y = if (period == "AM") (-6).dp else 0.dp)
                    )
                }
            }
        }
    }
}
