package com.mikeapp.timer.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun RepInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit,
) {
    var numberInput: String by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = { Text("Enter a Number") },
        text = {
            OutlinedTextField(
                value = numberInput,
                onValueChange = { input: String ->
                    if (input.all { it.isDigit() }) {
                        numberInput = input
                    }
                },
                label = { Text("Number") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss.invoke()
                onConfirm.invoke(numberInput.toLongOrNull() ?: 0)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss.invoke()
            }) {
                Text("Cancel")
            }
        }
    )
}