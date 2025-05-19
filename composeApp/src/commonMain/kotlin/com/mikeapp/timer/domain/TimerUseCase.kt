package com.mikeapp.timer.domain

import com.mikeapp.timer.database.DatabaseHelper

class TimerUseCase(private val databaseHelper: DatabaseHelper) {

    // ==== timer_data ====
    fun saveTimerData(total: Long, divider: Long) {
        databaseHelper.saveTimerData(total, divider)
    }

    fun getTimerData(): Pair<Long, Long>? {
        return databaseHelper.getTimerData()?.let {
            Pair(it.total, it.divider)
        }
    }

    // ==== timer_record ====
    fun insertTimeRecord(time: String) {
        databaseHelper.insertTimeRecord(time)
    }

    fun deleteLastTimeRecord() {
        databaseHelper.deleteLastTimeRecord()
    }

    fun clearAllTimeRecords() {
        databaseHelper.clearAllTimeRecords()
    }

    fun getAllTimeRecords(): List<String> {
        return databaseHelper.getAllTimeRecords().map { it.time }
    }

    // ==== reps_record ====
    fun insertRep(rep: Long) {
        databaseHelper.insertRep(rep)
    }

    fun deleteRepById(id: Long) {
        databaseHelper.deleteRepById(id)
    }

    fun clearAllReps() {
        databaseHelper.clearAllReps()
    }

    fun getAllReps(): List<Long> {
        return databaseHelper.getAllReps().map { it.rep }
    }
}
