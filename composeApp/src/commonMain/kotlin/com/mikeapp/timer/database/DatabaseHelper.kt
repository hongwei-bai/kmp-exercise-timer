package com.mikeapp.timer.database

class DatabaseHelper(driverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(driverFactory.createDriver())
    private val timerConfigQueries = database.timerConfigQueries
    private val timeRecordQueries = database.timeRecordQueries
    private val repsRecordQueries = database.repsRecordQueries

    // ==== timer_config ====

    /** Inserts or updates the single timer config row (id = 1). */
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
        timerConfigQueries.upsertConfig(
            reminderMinutes = reminderMinutes,
            alarmMinutes = alarmMinutes,
            isReminderMute = if (isReminderMute) 1 else 0,
            isAlarmMute = if (isAlarmMute) 1 else 0,
            reminderState = reminderState,
            alarmState = alarmState,
            reminderTime = reminderTime,
            alarmTime = alarmTime
        )
    }

    /** Returns the single config row if it exists. */
    fun getTimerConfig(): Timer_config? {
        return timerConfigQueries.getConfig().executeAsOneOrNull()
    }

    /** Clears the config (deletes the row). */
    fun clearTimerConfig() {
        timerConfigQueries.clearConfig()
    }

    // ==== timer_record ====

    // ==== time_record ====

    /** Clears all records and inserts the full list. */
    fun saveAllTimeRecords(times: List<Long>) {
        timerConfigQueries.transaction {
            timeRecordQueries.clearTimes()
            times.forEach { time ->
                timeRecordQueries.insertTime(time)
            }
        }
    }

    /** Gets all records in insertion order. */
    fun getAllTimeRecords(): List<Time_record> {
        return timeRecordQueries.selectAllTimes().executeAsList()
    }

    /** Deletes all time records. */
    fun clearAllTimeRecords() {
        timeRecordQueries.clearTimes()
    }

    // ==== reps_record ====

    /** Clears all records and inserts the full list. */
    fun saveAllReps(reps: List<Long>) {
        repsRecordQueries.transaction {
            repsRecordQueries.clearReps()
            reps.forEach { rep ->
                repsRecordQueries.insertRep(rep)
            }
        }
    }

    /** Gets all reps records ordered by insertion. */
    fun getAllReps(): List<Reps_record> {
        return repsRecordQueries.selectAllReps().executeAsList()
    }

    /** Clears all reps records. */
    fun clearAllReps() {
        repsRecordQueries.clearReps()
    }
}
