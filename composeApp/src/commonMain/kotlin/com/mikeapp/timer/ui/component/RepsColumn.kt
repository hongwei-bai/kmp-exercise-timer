package com.mikeapp.timer.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.pow

@Composable
fun RepsColumn(
    reps: List<Long>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    val maxAlpha = 1.0f
    val minAlpha = 0.3f
    val decayFactor = 1.5f
    val totalItems = reps.size.coerceAtLeast(1)

    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll to bottom when number of reps changes
    LaunchedEffect(reps.size) {
        if (reps.isNotEmpty()) {
            coroutineScope.launch {
                gridState.animateScrollToItem(reps.lastIndex)
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 56.dp),
        state = gridState,
        modifier = modifier
    ) {
        items(reps.size) { index ->
            val alpha = if (reps.size < 4) {
                maxAlpha
            } else {
                val reverseIndex = totalItems - index - 1
                val normalized = reverseIndex.toFloat() / (totalItems - 1).coerceAtLeast(1)
                minAlpha + (maxAlpha - minAlpha) * (1f - normalized.pow(decayFactor))
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .graphicsLayer { this.alpha = alpha }
            ) {
                CircleBubble(
                    "${reps[index]}",
                    onClick = { text ->
                        text.toIntOrNull()?.let { number ->
                            onClick.invoke(number)
                        }
                    },
                    isSelected = false
                )
            }
        }
    }
}