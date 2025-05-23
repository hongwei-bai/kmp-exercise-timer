package com.mikeapp.timer.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ClearAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = { Text("Confirmation") },
        text = { Text("Are you sure you want to clear all records?") },
        confirmButton = {
            Button(onClick = {
                onDismiss.invoke()
                onConfirm.invoke()
            }) {
                Text("Confirm Clear")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss.invoke()
            }) {
                Text("Cancel")
            }
        }
    )
}