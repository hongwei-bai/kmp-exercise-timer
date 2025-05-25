package com.mikeapp.timer.data

import com.mikeapp.timer.database.DatabaseHelper

class TimerRepository(private val databaseHelper: DatabaseHelper) {

    // ==== timer_config ====
    fun saveTimerConfig(
        reminderMinutes: Long,
        alarmMinutes: Long,
        isReminderMute: Boolean,
        isAlarmMute: Boolean,
        reminderState: String,
        alarmState: String,
        reminderTime: Long,
        alarmTime: Long
    ) {
        databaseHelper.saveTimerConfig(
            reminderMinutes = reminderMinutes,
            alarmMinutes = alarmMinutes,
            isReminderMute = isReminderMute,
            isAlarmMute = isAlarmMute,
            reminderState = reminderState,
            alarmState = alarmState,
            reminderTime = reminderTime,
            alarmTime = alarmTime
        )
    }

    fun getTimerConfig(): TimerConfig? {
        return databaseHelper.getTimerConfig()?.let {
            TimerConfig(
                reminderMinutes = it.reminderMinutes,
                alarmMinutes = it.alarmMinutes,
                isReminderMute = it.isReminderMute != 0L,
                isAlarmMute = it.isAlarmMute != 0L,
                reminderState = it.reminderState,
                alarmState = it.alarmState,
                reminderTime = it.reminderTime,
                alarmTime = it.alarmTime
            )
        }
    }

    fun clearTimerConfig() {
        databaseHelper.clearTimerConfig()
    }

    // ==== timer_record ====

    fun saveAllTimeRecords(times: List<Long>) {
        databaseHelper.saveAllTimeRecords(times)
    }

    fun clearAllTimeRecords() {
        databaseHelper.clearAllTimeRecords()
    }

    fun getAllTimeRecords(): List<Long> {
        return databaseHelper.getAllTimeRecords().map { it.time }
    }

    // ==== reps_record ====

    fun saveAllReps(reps: List<Long>) {
        databaseHelper.saveAllReps(reps)
    }

    fun clearAllReps() {
        databaseHelper.clearAllReps()
    }

    fun getAllReps(): List<Long> {
        return databaseHelper.getAllReps().map { it.rep }
    }
}
