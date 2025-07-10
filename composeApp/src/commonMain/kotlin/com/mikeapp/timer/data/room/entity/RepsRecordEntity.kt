package com.mikeapp.timer.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reps_record")
data class RepsRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val rep: Int
)