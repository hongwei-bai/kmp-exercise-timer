package com.mikeapp.timer.data.room.dao

import androidx.room.*
import com.mikeapp.timer.data.room.entity.TimerConfigEntity

@Dao
interface TimerConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateConfig(config: TimerConfigEntity)

    @Query("SELECT * FROM timer_config LIMIT 1")
    suspend fun getConfig(): TimerConfigEntity?

    @Query("DELETE FROM timer_config")
    suspend fun clearConfig()
}