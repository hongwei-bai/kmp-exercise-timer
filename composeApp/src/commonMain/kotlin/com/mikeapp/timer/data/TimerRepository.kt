package com.mikeapp.timer.data

import com.mikeapp.timer.database.DatabaseHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class TimerRepository(private val databaseHelper: DatabaseHelper) {

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
    fun insertTimeRecord(time: Long) {
        databaseHelper.insertTimeRecord(time)
    }

    fun deleteLastTimeRecord() {
        databaseHelper.deleteLastTimeRecord()
    }

    fun clearAllTimeRecords() {
        databaseHelper.clearAllTimeRecords()
    }

    fun getAllTimeRecords(): List<Long> {
        return databaseHelper.getAllTimeRecords().map { it.time }
    }

    fun observeAllTimeRecords(): Flow<List<Long>> {
        return databaseHelper.observeAllTimeRecords().map { list -> list.map { it.time } }
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

    fun observeAllReps(): Flow<List<Long>> {
        return databaseHelper.observeAllReps().map { list -> list.map { it.rep } }
    }
}
