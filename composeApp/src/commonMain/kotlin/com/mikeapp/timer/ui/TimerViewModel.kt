package com.mikeapp.timer.ui

import com.mikeapp.timer.alarm.AlarmSetter
import com.mikeapp.timer.data.TimerRepository
import com.mikeapp.timer.notification.Notification
import com.mikeapp.timer.notification.NotificationCategory
import com.mikeapp.timer.ui.HomeScreenConfig.alarmNotificationMessage
import com.mikeapp.timer.ui.HomeScreenConfig.alarmNotificationTitle
import com.mikeapp.timer.ui.HomeScreenConfig.reminderNotificationMessage
import com.mikeapp.timer.ui.HomeScreenConfig.reminderNotificationTitle
import com.mikeapp.timer.ui.alarmscheme.AlarmState
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.getAlarmTime
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.getName
import com.mikeapp.timer.ui.base.BaseViewModel
import com.mikeapp.timer.ui.viewstate.TimeConfigViewState
import com.mikeapp.timer.ui.viewstate.map
import kotlinx.coroutines.launch

// commonMain
class TimerViewModel(
    private val repository: TimerRepository
) : BaseViewModel() {

    fun showReminderNotification() {
        Notification.showNotification(reminderNotificationTitle, reminderNotificationMessage,
            NotificationCategory.Reminder)
    }

    fun showAlarmNotification() {
        Notification.showNotification(alarmNotificationTitle, alarmNotificationMessage,
            NotificationCategory.Alarm)
    }

    fun setReminder(time: Long) {
        AlarmSetter.setAlarm(time, reminderNotificationTitle, reminderNotificationMessage)
    }

    fun cancelReminder() {
        AlarmSetter.cancelAlarm(reminderNotificationTitle, reminderNotificationMessage)
    }

    fun setAlarm(time: Long) {
        AlarmSetter.setAlarm(time, alarmNotificationTitle, alarmNotificationMessage)
    }

    fun cancelAlarm() {
        AlarmSetter.cancelAlarm(alarmNotificationTitle, alarmNotificationMessage)
    }

    fun saveTimeConfig(
        reminderMinutes: Long,
        alarmMinutes: Long,
        isReminderMute: Boolean,
        isAlarmMute: Boolean,
        reminderState: AlarmState,
        alarmState: AlarmState
    ) {
        println("save time config, reminderState: $reminderState")
        repository.saveTimerConfig(
            reminderMinutes = reminderMinutes,
            alarmMinutes = alarmMinutes,
            isReminderMute = isReminderMute,
            isAlarmMute = isAlarmMute,
            reminderState = reminderState.getName(),
            alarmState = alarmState.getName(),
            reminderTime = reminderState.getAlarmTime(),
            alarmTime = alarmState.getAlarmTime()
        )
    }

    fun restoreTimeConfig(onResult: (TimeConfigViewState) -> Unit) {
        viewModelScope.launch {
            val timerConfig = repository.getTimerConfig()?.map()
            println("restore time config, reminderState: ${timerConfig?.reminderState}")
            timerConfig?.let {
                onResult(it)
            }
        }
    }

    fun saveTimeRecords(records: List<Long>) {
        viewModelScope.launch {
            repository.saveAllTimeRecords(records)
        }
    }

    fun restoreTimeRecords(onResult: (List<Long>) -> Unit) {
        viewModelScope.launch {
            val records = repository.getAllTimeRecords()
            onResult(records)
        }
    }

    fun saveReps(reps: List<Long>) {
        viewModelScope.launch {
            repository.saveAllReps(reps)
        }
    }

    fun restoreReps(onResult: (List<Long>) -> Unit) {
        viewModelScope.launch {
            val reps = repository.getAllReps()
            onResult(reps)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAllReps()
            repository.clearAllTimeRecords()
            repository.clearTimerConfig()
        }
    }
}