package com.mikeapp.timer.ui.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleBubble(
    text: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animation for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale_animation"
    )

    // Selection animation
    val selectionScale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "selection_animation"
    )

    // Gradient colors based on selection state
    val backgroundGradient = if (isSelected) {
        Brush.radialGradient(
            colors = listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.primary.copy(alpha = 0.8f)
            )
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                MaterialTheme.colors.secondary,
                MaterialTheme.colors.secondary.copy(alpha = 0.9f)
            )
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(56.dp)
            .scale(scale * selectionScale)
            .shadow(
                elevation = if (isSelected) 12.dp else 6.dp,
                shape = CircleShape,
                ambientColor = if (isSelected) {
                    MaterialTheme.colors.primary.copy(alpha = 0.3f)
                } else {
                    Color.Black.copy(alpha = 0.1f)
                }
            )
            .clip(CircleShape)
            .background(brush = backgroundGradient)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        val released = tryAwaitRelease()
                        isPressed = false
                        if (released) {
                            onClick.invoke(text)
                        }
                    }
                )
            }
    ) {
        Text(
            text = text,
            color = if (isSelected) {
                MaterialTheme.colors.onPrimary
            } else {
                MaterialTheme.colors.onSecondary
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        // Subtle glow effect for selected state
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.15f),
                                Color.Transparent
                            ),
                            radius = 28f
                        )
                    )
            )
        }
    }
}