package com.mikeapp.timer.ui.viewstate

import com.mikeapp.timer.data.TimerConfig
import com.mikeapp.timer.ui.alarmscheme.AlarmState
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.nameActive
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.nameAlarming
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.namePaused

fun TimerConfig.map(): TimeConfigViewState =
    TimeConfigViewState(
        reminderMinutes = reminderMinutes,
        alarmMinutes = alarmMinutes,
        isReminderMute = isReminderMute,
        isAlarmMute = isAlarmMute,
        reminderState = when (reminderState) {
            nameActive -> AlarmState.Active(reminderTime)
            namePaused -> AlarmState.Paused(reminderTime)
            nameAlarming -> AlarmState.Alarming
            else -> AlarmState.Inactive
        },
        alarmState = when (alarmState) {
            nameActive -> AlarmState.Active(alarmTime)
            namePaused -> AlarmState.Paused(alarmTime)
            nameAlarming -> AlarmState.Alarming
            else -> AlarmState.Inactive
        }
    )