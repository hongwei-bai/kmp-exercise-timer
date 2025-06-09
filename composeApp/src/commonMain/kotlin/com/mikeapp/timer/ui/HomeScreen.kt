package com.mikeapp.timer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAlarm
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikeapp.timer.lifecycle.AppLifecycle
import com.mikeapp.timer.ui.alarmscheme.AlarmState
import com.mikeapp.timer.ui.component.*
import com.mikeapp.timer.ui.util.MS_PER_MINUTE
import com.mikeapp.timer.ui.util.getCurrentTimeLong
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

@Preview
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen() {
    val viewModel: TimerViewModel = getKoin().get()

    // Ui state
    var currentTimeLong by remember { mutableStateOf(getCurrentTimeLong()) }
    val timeRecords = remember { mutableStateListOf<Long>() }
    val reps = remember { mutableStateListOf<Long>() }
    val progressBarMaxMinute = remember { mutableIntStateOf(3) }
    val progressBarDividerMinute = remember { mutableIntStateOf(2) }

    // Dialog flags
    var showClearConfirmationDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }
    var showWarningInputDialog by remember { mutableStateOf(false) }
    var showAlarmInputDialog by remember { mutableStateOf(false) }

    // For Alarms
    var warningState by remember { mutableStateOf<AlarmState>(AlarmState.Inactive) }
    var alarmState by remember { mutableStateOf<AlarmState>(AlarmState.Inactive) }
    val isDividerMuted = remember { mutableStateOf(true) }
    val isAlarmMuted = remember { mutableStateOf(false) }
    val onForegroundActive = remember { mutableStateOf(false) }

//    println("warningState: $warningState, time: ${formatMillisTo24hTime(currentTimeLong)}")
    when (warningState) {
        is AlarmState.Active -> if (currentTimeLong >= (warningState as AlarmState.Active).alarmTime) {
            warningState = AlarmState.Alarming
        }

        AlarmState.Alarming -> {
            viewModel.showReminderNotification()
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
            viewModel.showAlarmNotification()
            alarmState = AlarmState.Inactive
        }

        else -> Unit
    }

    fun onWarnConfigChanged() {
        val lastTimeRecord = timeRecords.lastOrNull()
        if (lastTimeRecord == null) {
            warningState = AlarmState.Inactive
            return
        }
        val comingAlarmTime = lastTimeRecord + (progressBarDividerMinute.intValue * MS_PER_MINUTE)
        when (warningState) {
            AlarmState.Inactive -> if (comingAlarmTime > currentTimeLong) {
                if (isDividerMuted.value) {
                    warningState = AlarmState.Paused(comingAlarmTime)
                } else {
                    warningState = AlarmState.Active(comingAlarmTime)
                }
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

    fun onWarnMuteChanged(isActive: Boolean) {
        when {
            isActive && warningState is AlarmState.Paused -> {
                warningState = AlarmState.Active((warningState as AlarmState.Paused).alarmTime)
            }

            !isActive && warningState is AlarmState.Active -> {
                warningState = AlarmState.Paused((warningState as AlarmState.Active).alarmTime)
            }
        }
    }

    fun onAlarmConfigChanged() {
        val lastTimeRecord = timeRecords.lastOrNull()
        if (lastTimeRecord == null) {
            alarmState = AlarmState.Inactive
            return
        }
        val comingAlarmTime = lastTimeRecord + (progressBarMaxMinute.intValue * MS_PER_MINUTE)
        when (alarmState) {
            AlarmState.Inactive -> if (comingAlarmTime > currentTimeLong) {
                if (isAlarmMuted.value) {
                    alarmState = AlarmState.Paused(comingAlarmTime)
                } else {
                    alarmState = AlarmState.Active(comingAlarmTime)
                }
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

    fun onAlarmMuteChanged(isActive: Boolean) {
        when {
            isActive && alarmState is AlarmState.Paused -> {
                alarmState = AlarmState.Active((alarmState as AlarmState.Paused).alarmTime)
            }

            !isActive && alarmState is AlarmState.Active -> {
                alarmState = AlarmState.Paused((alarmState as AlarmState.Active).alarmTime)
            }
        }
    }

    val lifecycle = remember { AppLifecycle() }
    LaunchedEffect(Unit) {
        lifecycle.observeLifecycle(
            onEnterForeground = {
                println("🌞 App entered foreground")
                onForegroundActive.value = true
                viewModel.run {
                    cancelReminder()
                    cancelAlarm()
                    restoreTimeRecords {
//                        println("restoreTimeRecords got called: ${it.size}")
                        timeRecords.clear()
                        timeRecords.addAll(it)
                    }
                    restoreReps {
                        reps.clear()
                        reps.addAll(it)
                    }
                    restoreTimeConfig {
                        progressBarDividerMinute.intValue = it.reminderMinutes.toInt()
                        progressBarMaxMinute.intValue = it.alarmMinutes.toInt()
                        isDividerMuted.value = it.isReminderMute
                        isAlarmMuted.value = it.isAlarmMute
                        warningState = it.reminderState
                        alarmState = it.alarmState
                    }
                }
            },
            onEnterBackground = {
                println("🌚 App entered background")
                onForegroundActive.value = false
                viewModel.run {
                    if (warningState is AlarmState.Active) {
                        setReminder((warningState as AlarmState.Active).alarmTime)
                    }
                    if (alarmState is AlarmState.Active) {
                        setAlarm((alarmState as AlarmState.Active).alarmTime)
                    }
                    saveTimeRecords(timeRecords)
                    saveReps(reps)
                    saveTimeConfig(
                        reminderMinutes = progressBarDividerMinute.intValue.toLong(),
                        alarmMinutes = progressBarMaxMinute.intValue.toLong(),
                        isReminderMute = isDividerMuted.value,
                        isAlarmMute = isAlarmMuted.value,
                        reminderState = warningState,
                        alarmState = alarmState
                    )
                }
            }
        )
    }

    LaunchedEffect(onForegroundActive.value) {
        while (onForegroundActive.value) {
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
            viewModel.clearAll()
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
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .weight(HomeScreenConfig.homeScreenTopRecordHeightWeight)
        ) {
            TimeRecordsColumn(
                timeRecords = timeRecords,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .heightIn(min = 240.dp)
                    .weight(0.4f)
            )

            RepsColumn(
                reps = reps,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(0.5f)
            ) {
                reps.removeAt(it)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            BigTimerTile(
                currentTimeLong = currentTimeLong,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .weight(HomeScreenConfig.homeScreenBottomButtonHeightWeight)
        ) {
            if (timeRecords.isNotEmpty()) {
                TimerProgressBar(
                    totalDuration = progressBarMaxMinute.intValue * 60 * 1000L,
                    currentTime = currentTimeLong - timeRecords.last(),
                    totalMinutes = progressBarMaxMinute.intValue,
                    dividerMinute = progressBarDividerMinute.intValue,
                    isDividerMuted = isDividerMuted.value,
                    isEndMuted = isAlarmMuted.value,
                    onDividerBellClicked = {
                        isDividerMuted.value = !isDividerMuted.value
                        onWarnMuteChanged(!isDividerMuted.value)
                    },
                    onAlarmBellClicked = {
                        isAlarmMuted.value = !isAlarmMuted.value
                        onAlarmMuteChanged(!isAlarmMuted.value)
                    }
                )
            }

            BigTapButtonGroup(
                onTapClick = {
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
                modifier = Modifier.weight(0.4f).wrapContentHeight()
            )

            Spacer(modifier = Modifier.weight(0.01f))

            // reps buttons
            RepsButtonGroup(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .wrapContentHeight()
                    .weight(0.15f),
                onCustomisedRepButtonClick = { showInputDialog = true }
            ) {
                reps.add(it.toLong())
            }

            Spacer(modifier = Modifier.weight(0.01f))

            //settings buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .wrapContentHeight()
                    .weight(0.24f)
            ) {
                val buttonHeight = 96.dp
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
                    },
                    modifier = Modifier
                        .height(buttonHeight)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.width(24.dp))
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
                    },
                    modifier = Modifier
                        .height(buttonHeight)
                        .aspectRatio(1f)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(0.1f, fill = false)
                .heightIn(min = 8.dp, max = 24.dp)
        )
    }
}