package com.mikeapp.timer.data.room.dao

import androidx.room.*
import com.mikeapp.timer.data.room.entity.TimeRecordEntity

@Dao
interface TimeRecordDao {

    @Insert
    suspend fun insertTime(timeRecord: TimeRecordEntity)

    @Query("DELETE FROM time_record")
    suspend fun clearTimes()

    @Query("SELECT * FROM time_record ORDER BY id")
    suspend fun selectAllTimes(): List<TimeRecordEntity>
}