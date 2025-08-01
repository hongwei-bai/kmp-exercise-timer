package com.mikeapp.timer.ui

import com.mikeapp.timer.alarm.AlarmCategory
import com.mikeapp.timer.alarm.AlarmSetter
import com.mikeapp.timer.alarm.AlarmSound
import com.mikeapp.timer.data.TimerRepository
import com.mikeapp.timer.notification.Notification
import com.mikeapp.timer.notification.NotificationCategory
import com.mikeapp.timer.sound.SoundPlayer
import com.mikeapp.timer.ui.HomeScreenConfig.alarmNotificationMessage
import com.mikeapp.timer.ui.HomeScreenConfig.alarmNotificationTitle
import com.mikeapp.timer.ui.HomeScreenConfig.reminderNotificationMessage
import com.mikeapp.timer.ui.HomeScreenConfig.reminderNotificationTitle
import com.mikeapp.timer.ui.alarmscheme.AlarmState
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.getAlarmTime
import com.mikeapp.timer.ui.alarmscheme.AlarmState.Companion.getName
import com.mikeapp.timer.ui.base.BaseViewModel
import com.mikeapp.timer.ui.util.getCurrentTimeLong
import com.mikeapp.timer.ui.viewstate.TimeConfigViewState
import com.mikeapp.timer.ui.viewstate.map
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock.System.now

// commonMain
class TimerViewModel(
    private val repository: TimerRepository
) : BaseViewModel() {
    private val _currentTime = MutableStateFlow(getCurrentTimeLong())
    val currentTime: StateFlow<Long> = _currentTime

    private val _tabList = MutableStateFlow(mutableListOf<Any>())
    val tabList: StateFlow<List<Any>> = _tabList

    private var job: Job? = null

    fun startTimer() {
        if (job?.isActive == true) return // avoid duplicate

        job = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _currentTime.value = getCurrentTimeLong()
            }
        }
    }

    fun stopTimer() {
        job?.cancel()
        job = null
    }

    fun showReminderNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            Notification.showNotification(
                reminderNotificationTitle, reminderNotificationMessage,
                NotificationCategory.Reminder
            )
            SoundPlayer.playSound(AlarmSound.Alarm_996)
        }
    }

    fun showAlarmNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            Notification.showNotification(
                alarmNotificationTitle, alarmNotificationMessage,
                NotificationCategory.Alarm
            )
            SoundPlayer.playSound(AlarmSound.Alarm_buzzer_992)
        }
    }

    fun setReminder(time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            AlarmSetter.setAlarm(time, reminderNotificationTitle, reminderNotificationMessage, AlarmCategory.Reminder)
        }
    }

    fun cancelReminder() {
        viewModelScope.launch(Dispatchers.IO) {
            AlarmSetter.cancelAlarm(reminderNotificationTitle, reminderNotificationMessage, AlarmCategory.Reminder)
        }
    }

    fun setAlarm(time: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            AlarmSetter.setAlarm(time, alarmNotificationTitle, alarmNotificationMessage, AlarmCategory.Alarm)
        }
    }

    fun cancelAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            AlarmSetter.cancelAlarm(alarmNotificationTitle, alarmNotificationMessage, AlarmCategory.Alarm)
        }
    }

    fun saveTimeConfig(
        reminderMinutes: Long,
        alarmMinutes: Long,
        isReminderMute: Boolean,
        isAlarmMute: Boolean,
        reminderState: AlarmState,
        alarmState: AlarmState
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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
    }

    fun restoreTimeConfig(onResult: (TimeConfigViewState) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val timerConfig = repository.getTimerConfig()?.map()
            println("restore time config, reminderState: ${timerConfig?.reminderState}")
            timerConfig?.let {
                onResult(cancelAlarmIfAlarmedInBackground(it))
            }
        }
    }

    private fun cancelAlarmIfAlarmedInBackground(timeConfig: TimeConfigViewState): TimeConfigViewState {
        val timeConfigCopy = timeConfig.copy()
        if (timeConfig.reminderState is AlarmState.Active) {
            val nowLong = now().toEpochMilliseconds()
            if (nowLong > timeConfig.reminderState.getAlarmTime() + 1000L) {
                timeConfigCopy.reminderState = AlarmState.Paused(timeConfig.reminderState.getAlarmTime())
            }
        }
        if (timeConfig.alarmState is AlarmState.Active) {
            val nowLong = now().toEpochMilliseconds()
            if (nowLong > timeConfig.alarmState.getAlarmTime() + 1000L) {
                timeConfigCopy.alarmState = AlarmState.Paused(timeConfig.alarmState.getAlarmTime())
            }
        }
        return timeConfigCopy
    }

    fun saveTimeRecords(records: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllTimeRecords()
            repository.saveAllTimeRecords(records)
        }
    }

    fun restoreTimeRecords(onResult: (List<Long>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val records = repository.getAllTimeRecords()
            onResult(records)
        }
    }

    fun saveReps(reps: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllReps()
            repository.saveAllReps(reps)
        }
    }

    fun restoreReps(onResult: (List<Long>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val reps = repository.getAllReps()
            onResult(reps)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllReps()
            repository.clearAllTimeRecords()
            repository.clearTimerConfig()
        }
    }

    fun getStepSize(minutes: Int): Int {
        return when {
            minutes < 15 -> 1
            minutes < 30 -> 5
            minutes < 120 -> 10
            minutes < 180 -> 30
            else -> 60
        }
    }
}