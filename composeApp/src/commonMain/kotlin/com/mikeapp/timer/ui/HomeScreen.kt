package com.mikeapp.timer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikeapp.timer.lifecycle.AppLifecycle
import com.mikeapp.timer.notification.Notification
import com.mikeapp.timer.ui.component.*
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen() {
    val viewModel: TimerViewModel = getKoin().get()
    val reps by viewModel.reps.collectAsState(emptyList())

    var baselineTimeLong by remember { mutableStateOf(0L) }
    var currentTimeLong by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    val timeRecords = remember { mutableStateListOf<String>() }
    var showClearConfirmationDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }

    val progressBarMaxMinute = remember { mutableIntStateOf(3) }
    val progressBarDividerMinute = remember { mutableIntStateOf(2) }

    val lifecycle = remember { AppLifecycle() }
    LaunchedEffect(Unit) {
        lifecycle.observeLifecycle(
            onEnterForeground = {
                println("🌞 App entered foreground")
            },
            onEnterBackground = {
                println("🌚 App entered background")
            }
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = getCurrentTime()
            currentTimeLong = getCurrentTimeLong()
        }
    }

    if (showClearConfirmationDialog) {
        Notification.showNotification("test", "<UNK> <UNK> <UNK>")
        ClearAlertDialog(
            onDismiss = {
                showClearConfirmationDialog = false
            }
        ) {
            timeRecords.clear()
            viewModel.clearReps()
        }
    }

    if (showInputDialog) {
        RepInputDialog(
            onDismiss = {
                showInputDialog = false
            }
        ) {
            viewModel.addRep(it)
        }
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
                        CircleBubble(
                            "${reps[it]}",
                            onClick = { text ->
                                text.toIntOrNull()?.let { number ->
                                    viewModel.removeRep(number.toLong())
                                }
                            },
                            isSelected = false
                        )
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .weight(0.6f)
        ) {
            BigTimerTile(
                currentTime = currentTime,
                modifier = Modifier.weight(0.2f)
            )

            if (timeRecords.isNotEmpty()) {
                TimerProgressBar(
                    totalDuration = progressBarMaxMinute.intValue * 60 * 1000L,
                    currentTime = currentTimeLong - baselineTimeLong,
                    totalMinutes = progressBarMaxMinute.intValue,
                    dividerMinute = progressBarDividerMinute.intValue,
                    onWarning = {
                        Notification.showNotification("Warning", "It's $it minutes!")
                    },
                    onComplete = {
                        Notification.showNotification("Time is up!", "It's $it minutes!")
                    }
                )
            }

            BigTapButtonGroup(
                onTapClick = {
                    baselineTimeLong = getCurrentTimeLong()
                    timeRecords.add(currentTime)
                },
                onWithdrawClick = {
                    if (timeRecords.isNotEmpty()) {
                        timeRecords.removeLast()
                    }
                },
                onClearClick = { showClearConfirmationDialog = true },
                modifier = Modifier.weight(0.4f)
            )

            Spacer(modifier = Modifier.weight(0.02f))

            // reps buttons
            RepsButtonGroup(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .weight(0.15f),
                onCustomisedRepButtonClick = { showInputDialog = true }
            ) { viewModel.addRep(it.toLong()) }

            //settings buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(0.24f)
            ) {
                HalfCircleButtonPair(
                    topIcon = Icons.Filled.AddAlert,
                    bottomIcon = Icons.Filled.RemoveCircle,
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
                    topIcon = Icons.Filled.AddAlarm,
                    bottomIcon = Icons.Filled.RemoveCircle,
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
