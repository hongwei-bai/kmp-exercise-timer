/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mikeapp.timer.data.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.mikeapp.timer.data.room.dao.RepsRecordDao
import com.mikeapp.timer.data.room.dao.TimeRecordDao
import com.mikeapp.timer.data.room.dao.TimerConfigDao
import com.mikeapp.timer.data.room.entity.RepsRecordEntity
import com.mikeapp.timer.data.room.entity.TimeRecordEntity
import com.mikeapp.timer.data.room.entity.TimerConfigEntity

@Database(entities = [TimerConfigEntity::class, TimeRecordEntity::class, RepsRecordEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class TimerRoomDatabase : RoomDatabase() {
    abstract fun timerConfigDao(): TimerConfigDao

    abstract fun timeRecordDao(): TimeRecordDao

    abstract fun repsRecordDao(): RepsRecordDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<TimerRoomDatabase> {
    override fun initialize(): TimerRoomDatabase
}

internal const val DB_FILE_NAME = "melon_timer.db"
