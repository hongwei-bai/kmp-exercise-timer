package com.mikeapp.timer.data.room.dao

import androidx.room.*
import com.mikeapp.timer.data.room.entity.RepsRecordEntity

@Dao
interface RepsRecordDao {

    @Insert
    suspend fun insertRep(repRecord: RepsRecordEntity)

    @Query("DELETE FROM reps_record")
    suspend fun clearReps()

    @Query("SELECT * FROM reps_record ORDER BY id")
    suspend fun selectAllReps(): List<RepsRecordEntity>
}