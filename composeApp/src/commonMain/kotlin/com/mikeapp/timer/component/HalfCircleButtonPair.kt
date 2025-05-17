package com.mikeapp.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HalfCircleButtonPair(
    topButtonName: String,
    bottomButtonName: String,
    onTopClick: () -> Unit,
    onBottomClick: () -> Unit
) {
    val diameter = 80
    val textSize = 12.sp
    Box(
        modifier = Modifier.size(diameter.dp),
        contentAlignment = Alignment.Center
    ) {
        // Full circle background (for visualization)
        Box(
            modifier = Modifier
                .size(diameter.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.2f))
        )

        Column(
            modifier = Modifier
                .size(diameter.dp)
                .clip(CircleShape),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Top Half-Circle Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .drawWithContent {
                        clipRect(
                            bottom = size.height // Clip to top half
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    .background(MaterialTheme.colors.primary)
                    .clickable(onClick = onTopClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = topButtonName,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.button,
                    fontSize = textSize
                )
            }

            // Bottom Half-Circle Button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .drawWithContent {
                        clipRect(
                            top = 0f // Clip to bottom half
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    .background(MaterialTheme.colors.secondary)
                    .clickable(onClick = onBottomClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = bottomButtonName,
                    color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.button,
                    fontSize = textSize
                )
            }
        }
    }
}