package com.example.kmpdemo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun HomeScreen() {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    val timeRecords = remember { mutableStateListOf<String>() }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = getCurrentTime()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to clear all records?") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    // Handle confirm action here
                    timeRecords.clear()
                }) {
                    Text("Confirm Clear")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Scrollable time records at top-left
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 16.dp)
                .heightIn(min = 240.dp)
                .weight(0.4f)
                .verticalScroll(rememberScrollState())
        ) {
            timeRecords.forEach {
                Text(text = it, fontSize = 16.sp)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .weight(0.6f)
        ) {
            // Main content: Timer and button
            Text(
                text = currentTime,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.2f)
            )

            Button(
                onClick = {
                    timeRecords.add(currentTime)
                },
                shape = CircleShape,
                modifier = Modifier.size(160.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2E9F81))
            ) {
                Text("Tap", color = Color.White, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                onClick = {
                    showDialog = true
                },
                shape = CircleShape,
                modifier = Modifier.size(64.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
            ) {
                Text("Clear", color = Color.White, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}

fun getCurrentTime(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val h = now.hour.toString().padStart(1, '0')
    val m = now.minute.toString().padStart(2, '0')
    val s = now.second.toString().padStart(2, '0')
    return "$h:$m:$s"
}
