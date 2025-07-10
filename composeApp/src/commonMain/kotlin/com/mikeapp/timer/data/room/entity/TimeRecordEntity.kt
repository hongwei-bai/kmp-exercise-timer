package com.mikeapp.timer.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_record")
data class TimeRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val time: Long
)