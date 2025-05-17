package com.example.kmpdemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmpdemo.component.CircleBubble
import com.example.kmpdemo.component.HalfCircleButtonPair
import com.example.kmpdemo.component.TimerProgressBar
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen() {
    var baselineTimeLong by remember { mutableStateOf(0L) }
    var currentTimeLong by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    val timeRecords = remember { mutableStateListOf<String>() }
    val reps = remember { mutableStateListOf<Int>() }
    var showDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }

    val progressBarMaxMinute = remember { mutableIntStateOf(3) }
    val progressBarDividerMinute = remember { mutableIntStateOf(2) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = getCurrentTime()
            currentTimeLong = getCurrentTimeLong()
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
                    reps.clear()
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

    if (showInputDialog) {
        var numberInput by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showInputDialog = false },
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
                    // Handle the confirmed input here
                    showInputDialog = false
                    reps.add(numberInput.toInt())
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showInputDialog = false
                }) {
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
        Row(
            modifier = Modifier.fillMaxWidth()
                .weight(0.4f)
        ) {
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
                    Text(text = it, fontSize = 24.sp)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 56.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 16.dp)
                    .weight(0.5f)
            ) {
                items(reps.size) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        CircleBubble("${reps[it]}") { text ->
                            text.toIntOrNull()?.let { number ->
                                reps.remove(number)
                            }
                        }
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .weight(0.6f)
        ) {
            Text(
                text = currentTime,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.2f)
            )

            if (timeRecords.isNotEmpty()) {
                TimerProgressBar(
                    totalDuration = progressBarMaxMinute.intValue * 60 * 1000L,
                    currentTime = currentTimeLong - baselineTimeLong,
                    totalMinutes = progressBarMaxMinute.intValue,
                    dividerMinute = progressBarDividerMinute.intValue
                )
            }

            // main buttons: withdraw/Tap/clear
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .weight(0.4f)
            ) {
                Button(
                    onClick = {
                        if (timeRecords.isNotEmpty()) {
                            timeRecords.removeLast()
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(84.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface) // Material 2 surface color
                ) {
                    Text("Withdraw", color = MaterialTheme.colors.onSurface, fontSize = 9.sp) // Material 2 text color
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = {
                        baselineTimeLong = getCurrentTimeLong()
                        timeRecords.add(currentTime)
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(160.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary) // Material 2 primary color
                ) {
                    Text("Tap", color = MaterialTheme.colors.onPrimary, fontSize = 24.sp) // Material 2 onPrimary color
                }

                Spacer(Modifier.width(32.dp))

                Button(
                    onClick = {
                        showDialog = true
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(64.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface) // Material 2 surface color
                ) {
                    Text("Clear", color = MaterialTheme.colors.onSurface, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.weight(0.02f))

            // reps buttons
            val repsList = listOf(12, 20, 30, 40)
            val repsButtonDiameter = 64
            val repsButtonTextSize = 12
            val paddingBetweenReps = 12
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .weight(0.15f)
            ) {
                repsList.forEach {
                    Button(
                        onClick = { reps.add(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(repsButtonDiameter.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary) // Material 2 secondary color
                    ) {
                        Text(
                            "+$it",
                            color = MaterialTheme.colors.onSecondary,
                            fontSize = repsButtonTextSize.sp
                        ) // Material 2 onSecondary color
                    }
                    Spacer(Modifier.width(paddingBetweenReps.dp))
                }

                Button(
                    onClick = {
                        showInputDialog = true
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(repsButtonDiameter.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Text("+?", color = MaterialTheme.colors.onSecondary, fontSize = repsButtonTextSize.sp)
                }
            }

            //settings buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(0.24f)
            ) {
                HalfCircleButtonPair(
                    topButtonName = "+Warning",
                    bottomButtonName = "-Warning",
                    onTopClick = {
                        if (progressBarDividerMinute.intValue < progressBarMaxMinute.intValue) {
                            progressBarDividerMinute.intValue += 1
                        }
                    },
                    onBottomClick = {
                        if (progressBarDividerMinute.intValue > 0) {
                            progressBarDividerMinute.intValue -= 1
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                HalfCircleButtonPair(
                    topButtonName = "+Total",
                    bottomButtonName = "-Total",
                    onTopClick = {
                        progressBarMaxMinute.intValue += 1
                    },
                    onBottomClick = {
                        if (progressBarMaxMinute.intValue > 1) {
                            progressBarMaxMinute.intValue -= 1
                            if (progressBarDividerMinute.intValue > progressBarMaxMinute.intValue) {
                                progressBarDividerMinute.intValue = progressBarMaxMinute.intValue
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}

fun getCurrentTimeLong(): Long = Clock.System.now().toEpochMilliseconds()

fun getCurrentTime(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val h = now.hour.toString().padStart(1, '0')
    val m = now.minute.toString().padStart(2, '0')
    val s = now.second.toString().padStart(2, '0')
    return "$h:$m:$s"
}
