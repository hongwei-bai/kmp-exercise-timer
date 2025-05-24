package com.mikeapp.timer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TimeInputDialog(
    initMinutes: Int = 0,
    onDismiss: () -> Unit,
    onConfirm: (minutes: Int) -> Unit
) {
    val hours = initMinutes / 60
    val minutes = initMinutes % 60
    var hoursInput by remember { mutableStateOf(hours.toString()) }
    var minutesInput by remember { mutableStateOf(minutes.toString()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Enter Time") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = hoursInput,
                        onValueChange = { if (it.all(Char::isDigit)) hoursInput = it },
                        label = { Text("Hours") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = minutesInput,
                        onValueChange = { if (it.all(Char::isDigit)) minutesInput = it },
                        label = { Text("Minutes") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val hours = hoursInput.toIntOrNull() ?: 0
                val minutes = minutesInput.toIntOrNull() ?: 0
                onDismiss()
                onConfirm(hours * 60 + minutes)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
