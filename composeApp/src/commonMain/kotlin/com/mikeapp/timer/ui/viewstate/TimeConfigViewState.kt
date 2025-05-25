package com.mikeapp.timer.ui.viewstate

import com.mikeapp.timer.ui.alarmscheme.AlarmState

data class TimeConfigViewState(
    val reminderMinutes: Long,
    val alarmMinutes: Long,
    val isReminderMute: Boolean,
    val isAlarmMute: Boolean,
    val reminderState: AlarmState,
    val alarmState: AlarmState
)