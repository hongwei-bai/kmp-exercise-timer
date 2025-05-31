package com.mikeapp.timer.ui.viewstate

import com.mikeapp.timer.ui.alarmscheme.AlarmState

data class TimeConfigViewState(
    val reminderMinutes: Long,
    val alarmMinutes: Long,
    val isReminderMute: Boolean,
    val isAlarmMute: Boolean,
    var reminderState: AlarmState,
    var alarmState: AlarmState
)