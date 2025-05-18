package com.mikeapp.timer.database

class DatabaseHelper(driverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(driverFactory.createDriver())
    private val timerConfigQueries = database.timerConfigQueries
    private val timeRecordQueries = database.timeRecordQueries
    private val repsRecordQueries = database.repsRecordQueries

    // ==== timer_data ====
    fun saveTimerData(total: Long, divider: Long) {
        // Assumes only 1 row exists with id = 1
        timerConfigQueries.insertOrReplace(total, divider)
    }

    fun getTimerData(): Timer_config? {
        return timerConfigQueries.selectOne().executeAsOneOrNull()
    }

    // ==== timer_record ====

    /** Inserts a new time record (as a string). */
    fun insertTimeRecord(time: String) {
        timeRecordQueries.insertTime(time)
    }

    /** Deletes the last inserted time record based on highest ID. */
    fun deleteLastTimeRecord() {
        timeRecordQueries.deleteLastTime()
    }

    /** Deletes all time records. */
    fun clearAllTimeRecords() {
        timeRecordQueries.clearTimes()
    }

    /** Gets all time records in insertion order (by ID). */
    fun getAllTimeRecords(): List<Time_record> {
        return timeRecordQueries.selectAllTimes().executeAsList()
    }

    // ==== reps_record ====

    /** Inserts a new reps record. */
    fun insertRep(rep: Long) {
        repsRecordQueries.insertRep(rep)
    }

    /** Deletes a reps record by its ID. */
    fun deleteRepById(id: Long) {
        repsRecordQueries.deleteRepById(id)
    }

    /** Clears all reps records. */
    fun clearAllReps() {
        repsRecordQueries.clearReps()
    }

    /** Returns all reps records ordered by ID. */
    fun getAllReps(): List<Reps_record> {
        return repsRecordQueries.selectAllReps().executeAsList()
    }
}