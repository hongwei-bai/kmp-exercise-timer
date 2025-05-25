package com.mikeapp.timer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAlarm
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikeapp.timer.lifecycle.AppLifecycle
import com.mikeapp.timer.notification.Notification
import com.mikeapp.timer.ui.alarmscheme.AlarmState
import com.mikeapp.timer.ui.component.*
import com.mikeapp.timer.ui.util.MS_PER_MINUTE
import com.mikeapp.timer.ui.util.formatMillisTo24hTime
import com.mikeapp.timer.ui.util.getCurrentTimeLong
import kotlinx.coroutines.delay
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen() {
    val viewModel: TimerViewModel = getKoin().get()

    // Ui state
    var currentTimeLong by remember { mutableStateOf(0L) }
    val timeRecords = remember { mutableStateListOf<Long>() }
    val reps = remember { mutableStateListOf<Long>() }
    val progressBarMaxMinute = remember { mutableIntStateOf(3) }
    val progressBarDividerMinute = remember { mutableIntStateOf(2) }

    // Dialog flags
    var showClearConfirmationDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }
    var showWarningInputDialog by remember { mutableStateOf(false) }
    var showAlarmInputDialog by remember { mutableStateOf(false) }

    // Useful? - TBD
    var baselineTimeLong by remember { mutableStateOf(0L) }

    // For Alarms
    var warningState by remember { mutableStateOf<AlarmState>(AlarmState.Inactive) }
    var alarmState by remember { mutableStateOf<AlarmState>(AlarmState.Inactive) }
    val isDividerMuted = remember { mutableStateOf(false) }
    val isEndMuted = remember { mutableStateOf(false) }

//    println("warningState: $warningState, time: ${formatMillisTo24hTime(currentTimeLong)}")
    when (warningState) {
        is AlarmState.Active -> if (currentTimeLong >= (warningState as AlarmState.Active).alarmTime) {
            warningState = AlarmState.Alarming
        }

        AlarmState.Alarming -> {
            Notification.showNotification("Reminder", "Reminder time is coming!")
            warningState = AlarmState.Inactive
        }

        else -> Unit
    }

//    println("alarmState: $alarmState, time: ${formatMillisTo24hTime(currentTimeLong)}")
    when (alarmState) {
        is AlarmState.Active -> if (currentTimeLong >= (alarmState as AlarmState.Active).alarmTime) {
            alarmState = AlarmState.Alarming
        }

        AlarmState.Alarming -> {
            Notification.showNotification("Alarm", "Time is up!!!!!!!!!!!!!!!")
            alarmState = AlarmState.Inactive
        }

        else -> Unit
    }

    fun onWarnConfigChanged() {
        val lastTimeRecord = timeRecords.lastOrNull() ?: return
        val comingAlarmTime = lastTimeRecord + (progressBarDividerMinute.intValue * MS_PER_MINUTE)
        if (isDividerMuted.value) {
            if (warningState is AlarmState.Active) {
                warningState = AlarmState.Paused((warningState as AlarmState.Active).alarmTime)
            } else {
                warningState = AlarmState.Paused(0)
            }
        } else when (warningState) {
            AlarmState.Inactive -> if (comingAlarmTime > currentTimeLong) {
                warningState = AlarmState.Active(comingAlarmTime)
            }

            is AlarmState.Active -> warningState = if (comingAlarmTime > currentTimeLong) {
                AlarmState.Active(comingAlarmTime)
            } else {
                AlarmState.Inactive
            }

            AlarmState.Alarming -> warningState = if (comingAlarmTime > currentTimeLong) {
                AlarmState.Active(comingAlarmTime)
            } else {
                AlarmState.Inactive
            }

            is AlarmState.Paused -> if (comingAlarmTime > currentTimeLong) {
                warningState = AlarmState.Paused(comingAlarmTime)
            }
        }
    }

    fun onAlarmConfigChanged() {
        val lastTimeRecord = timeRecords.lastOrNull() ?: return
        val comingAlarmTime = lastTimeRecord + (progressBarMaxMinute.intValue * MS_PER_MINUTE)
        if (isEndMuted.value) {
            if (alarmState is AlarmState.Active) {
                alarmState = AlarmState.Paused((alarmState as AlarmState.Active).alarmTime)
            } else {
                alarmState = AlarmState.Paused(0)
            }
        } else when (alarmState) {
            AlarmState.Inactive -> if (comingAlarmTime > currentTimeLong) {
                alarmState = AlarmState.Active(comingAlarmTime)
            }

            is AlarmState.Active -> alarmState = if (comingAlarmTime > currentTimeLong) {
                AlarmState.Active(comingAlarmTime)
            } else {
                AlarmState.Inactive
            }

            AlarmState.Alarming -> alarmState = if (comingAlarmTime > currentTimeLong) {
                AlarmState.Active(comingAlarmTime)
            } else {
                AlarmState.Inactive
            }

            is AlarmState.Paused -> if (comingAlarmTime > currentTimeLong) {
                alarmState = AlarmState.Paused(comingAlarmTime)
            }
        }
    }

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
            currentTimeLong = getCurrentTimeLong()
        }
    }

    if (showClearConfirmationDialog) {
        ClearAlertDialog(
            onDismiss = {
                showClearConfirmationDialog = false
            }
        ) {
            timeRecords.clear()
            reps.clear()
            onWarnConfigChanged()
            onAlarmConfigChanged()
        }
    }

    if (showInputDialog) {
        RepInputDialog(
            onDismiss = {
                showInputDialog = false
            }
        ) {
            reps.add(it)
        }
    }

    if (showWarningInputDialog) {
        TimeInputDialog(
            initMinutes = progressBarDividerMinute.intValue,
            onDismiss = { showWarningInputDialog = false },
            onConfirm = { minutes ->
                progressBarDividerMinute.value = minutes
                onWarnConfigChanged()
            }
        )
    }

    if (showAlarmInputDialog) {
        TimeInputDialog(
            initMinutes = progressBarMaxMinute.intValue,
            onDismiss = { showAlarmInputDialog = false },
            onConfirm = { minutes ->
                progressBarMaxMinute.value = minutes
                onAlarmConfigChanged()
            }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Top row area for time records + reps - weight 0.3 height of screen height
        Row(
            modifier = Modifier.fillMaxWidth()
                .weight(HomeScreenConfig.homeScreenTopRecordHeightWeight)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .heightIn(min = 240.dp)
                    .weight(0.4f)
                    .verticalScroll(rememberScrollState())
            ) {
                timeRecords.forEach {
                    Text(text = formatMillisTo24hTime(it), fontSize = 24.sp)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 56.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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
                                    reps.remove(number.toLong())
                                }
                            },
                            isSelected = false
                        )
                    }
                }
            }
        }

        // The rest button area - take 0.7 weight of screen height
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .weight(HomeScreenConfig.homeScreenBottomButtonheightWeight)
        ) {
            BigTimerTile(
                currentTimeLong = currentTimeLong,
                modifier = Modifier.weight(0.2f)
            )

            if (timeRecords.isNotEmpty()) {
                TimerProgressBar(
                    totalDuration = progressBarMaxMinute.intValue * 60 * 1000L,
                    currentTime = currentTimeLong - baselineTimeLong,
                    totalMinutes = progressBarMaxMinute.intValue,
                    dividerMinute = progressBarDividerMinute.intValue,
                    isDividerMuted = isDividerMuted.value,
                    isEndMuted = isEndMuted.value,
                    onDividerBellClicked = {
                        isDividerMuted.value = !isDividerMuted.value
                        onWarnConfigChanged()
                    },
                    onAlarmBellClicked = {
                        isEndMuted.value = !isEndMuted.value
                        onAlarmConfigChanged()
                    }
                )
            }

            BigTapButtonGroup(
                onTapClick = {
                    baselineTimeLong = getCurrentTimeLong()
                    timeRecords.add(currentTimeLong)
                    onWarnConfigChanged()
                    onAlarmConfigChanged()
                },
                onWithdrawClick = {
                    if (timeRecords.isNotEmpty()) {
                        timeRecords.removeLast()
                        onWarnConfigChanged()
                        onAlarmConfigChanged()
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
            ) {
                reps.add(it.toLong())
            }

            Spacer(modifier = Modifier.weight(0.02f))

            //settings buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(0.24f)
            ) {
                HalfCircleButtonPair(
                    topIcon = Icons.Outlined.AddAlarm,
                    bottomIcon = Icons.Outlined.RemoveCircle,
                    onTopClick = {
                        if (progressBarDividerMinute.intValue < progressBarMaxMinute.intValue) {
                            progressBarDividerMinute.intValue += 1
                        }
                        onWarnConfigChanged()
                    },
                    onBottomClick = {
                        if (progressBarDividerMinute.intValue > 0) {
                            progressBarDividerMinute.intValue -= 1
                        }
                        onWarnConfigChanged()
                    },
                    onLongClick = {
                        showWarningInputDialog = true
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                HalfCircleButtonPair(
                    topIcon = Icons.Outlined.AddAlert,
                    bottomIcon = Icons.Outlined.RemoveCircle,
                    onTopClick = {
                        progressBarMaxMinute.intValue += 1
                        onAlarmConfigChanged()
                    },
                    onBottomClick = {
                        if (progressBarMaxMinute.intValue > 1) {
                            progressBarMaxMinute.intValue -= 1
                            if (progressBarDividerMinute.intValue > progressBarMaxMinute.intValue) {
                                progressBarDividerMinute.intValue = progressBarMaxMinute.intValue
                            }
                        }
                        onAlarmConfigChanged()
                    },
                    onLongClick = {
                        showAlarmInputDialog = true
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.05f))
    }
}