package com.mikeapp.timer.data

import com.mikeapp.timer.data.room.TimerRoomDatabase
import com.mikeapp.timer.data.room.entity.RepsRecordEntity
import com.mikeapp.timer.data.room.entity.TimeRecordEntity
import com.mikeapp.timer.data.room.entity.TimerConfigEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TimerRepository(
    private var roomDatabase: TimerRoomDatabase,
    private val scope: CoroutineScope
) {
    private val timerConfigDao = roomDatabase.timerConfigDao()
    private val timeRecordDao = roomDatabase.timeRecordDao()
    private val repsRecordDao = roomDatabase.repsRecordDao()

    // ==== timer_config ====
    suspend fun saveTimerConfig(
        reminderMinutes: Long,
        alarmMinutes: Long,
        isReminderMute: Boolean,
        isAlarmMute: Boolean,
        reminderState: String,
        alarmState: String,
        reminderTime: Long,
        alarmTime: Long
    ) = withContext(Dispatchers.IO) {
        val config = TimerConfigEntity(
            id = 1,
            reminderMinutes = reminderMinutes.toInt(),
            alarmMinutes = alarmMinutes.toInt(),
            isReminderMute = isReminderMute,
            isAlarmMute = isAlarmMute,
            reminderState = reminderState,
            alarmState = alarmState,
            reminderTime = reminderTime,
            alarmTime = alarmTime
        )
        timerConfigDao.updateConfig(config)
    }

    suspend fun getTimerConfig(): TimerConfig? = withContext(Dispatchers.IO) {
        timerConfigDao.getConfig()?.let {
            TimerConfig(
                reminderMinutes = it.reminderMinutes.toLong(),
                alarmMinutes = it.alarmMinutes.toLong(),
                isReminderMute = it.isReminderMute,
                isAlarmMute = it.isAlarmMute,
                reminderState = it.reminderState,
                alarmState = it.alarmState,
                reminderTime = it.reminderTime,
                alarmTime = it.alarmTime
            )
        }
    }

    suspend fun clearTimerConfig() = withContext(Dispatchers.IO) {
        timerConfigDao.clearConfig()
    }

    // ==== time_record ====

    suspend fun saveAllTimeRecords(times: List<Long>) = withContext(Dispatchers.IO) {
        val entities = times.map { TimeRecordEntity(time = it) }
        entities.forEach { timeRecordDao.insertTime(it) }
    }

    suspend fun clearAllTimeRecords() = withContext(Dispatchers.IO) {
        timeRecordDao.clearTimes()
    }

    suspend fun getAllTimeRecords(): List<Long> = withContext(Dispatchers.IO) {
        timeRecordDao.selectAllTimes().map { it.time }
    }

    // ==== reps_record ====

    suspend fun saveAllReps(reps: List<Long>) = withContext(Dispatchers.IO) {
        val entities = reps.map { RepsRecordEntity(rep = it.toInt()) }
        entities.forEach { repsRecordDao.insertRep(it) }
    }

    suspend fun clearAllReps() = withContext(Dispatchers.IO) {
        repsRecordDao.clearReps()
    }

    suspend fun getAllReps(): List<Long> = withContext(Dispatchers.IO) {
        repsRecordDao.selectAllReps().map { it.rep.toLong() }
    }
}
