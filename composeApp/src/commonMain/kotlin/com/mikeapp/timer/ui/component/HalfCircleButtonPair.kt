package com.mikeapp.timer.ui.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun HalfCircleButtonPair(
    topIcon: ImageVector,
    bottomIcon: ImageVector,
    onTopClick: () -> Unit,
    onBottomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val diameter = 96.dp

    // Animation states for press effects
    var isTopPressed by remember { mutableStateOf(false) }
    var isBottomPressed by remember { mutableStateOf(false) }

    val topScale by animateFloatAsState(
        targetValue = if (isTopPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "top_scale"
    )

    val bottomScale by animateFloatAsState(
        targetValue = if (isBottomPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "bottom_scale"
    )

    val topGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.primary.copy(alpha = 0.8f)
        )
    )

    val bottomGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.secondary.copy(alpha = 0.8f),
            MaterialTheme.colors.secondary
        )
    )

    Box(
        modifier = modifier.size(diameter),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(diameter)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .clip(CircleShape)
                .background(MaterialTheme.colors.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .scale(topScale)
                        .drawWithContent {
                            clipRect(bottom = size.height) {
                                this@drawWithContent.drawContent()
                            }
                        }
                        .background(topGradient)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isTopPressed = true
                                    val released = tryAwaitRelease()
                                    isTopPressed = false
                                    if (released) onTopClick()
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            imageVector = topIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .scale(bottomScale)
                        .drawWithContent {
                            clipRect(top = 0f) {
                                this@drawWithContent.drawContent()
                            }
                        }
                        .background(bottomGradient)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isBottomPressed = true
                                    val released = tryAwaitRelease()
                                    isBottomPressed = false
                                    if (released) onBottomClick()
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = bottomIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .size(10.dp)
                .shadow(2.dp, CircleShape)
                .background(MaterialTheme.colors.primaryVariant, CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colors.primaryVariant.copy(alpha = 0.7f),
                            MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f),
                            MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f),
                            MaterialTheme.colors.primaryVariant.copy(alpha = 0.1f),
                            MaterialTheme.colors.primaryVariant.copy(alpha = 0.01f)
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}
