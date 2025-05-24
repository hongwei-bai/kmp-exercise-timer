package com.mikeapp.timer.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BigTapButtonGroup(
    onTapClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                onWithdrawClick.invoke()
            },
            shape = CircleShape,
            modifier = Modifier.size(84.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface) // Material 2 surface color
        ) {
            Text("Withdraw", color = MaterialTheme.colors.onSurface, fontSize = 12.sp) // Material 2 text color
        }

        Spacer(Modifier.width(16.dp))

        Button(
            onClick = {
                onTapClick.invoke()
            },
            shape = CircleShape,
            modifier = Modifier.size(160.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary) // Material 2 primary color
        ) {
            Text("Tap", color = MaterialTheme.colors.onPrimary, fontSize = 32.sp) // Material 2 onPrimary color
        }

        Spacer(Modifier.width(32.dp))

        Button(
            onClick = {
                onClearClick.invoke()
            },
            shape = CircleShape,
            modifier = Modifier.size(64.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface) // Material 2 surface color
        ) {
            Text("Clear", color = MaterialTheme.colors.onSurface, fontSize = 12.sp)
        }
    }
}