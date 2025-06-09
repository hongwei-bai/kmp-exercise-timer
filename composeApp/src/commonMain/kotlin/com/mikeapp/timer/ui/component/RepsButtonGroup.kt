package com.mikeapp.timer.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RepsButtonGroup(
    modifier: Modifier = Modifier,
    onCustomisedRepButtonClick: () -> Unit,
    onClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val repsList = listOf(12, 20, 30, 40)
    val repsSizes = listOf(44, 48, 52, 56)
    val repsButtonDiameter = 56
    val repsButtonTextSize = 14
    val paddingBetweenReps = 4

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        if (expanded) {
            repsList.forEachIndexed { i, number ->
                Button(
                    onClick = { onClick(number) },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(horizontal = paddingBetweenReps.dp)
                        .requiredSize(repsSizes[i].dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "+$number",
                            color = MaterialTheme.colors.onSecondary,
                            fontSize = repsButtonTextSize.sp,
                            modifier = Modifier.offset(y = (-2.5).dp)
                        )
                    }
                }
            }
            Button(
                onClick = { onCustomisedRepButtonClick.invoke() },
                shape = CircleShape,
                modifier = Modifier
                    .padding(horizontal = paddingBetweenReps.dp)
                    .requiredSize(repsButtonDiameter.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "+?",
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = repsButtonTextSize.sp,
                        modifier = Modifier.offset(y = (-2.5).dp)
                    )
                }
            }
        }

        Icon(
            imageVector = if (expanded) Icons.Default.ChevronRight else Icons.Default.ChevronLeft,
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(32.dp)
                .clickable { expanded = !expanded }
        )
    }
}
