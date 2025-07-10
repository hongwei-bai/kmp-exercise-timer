package com.mikeapp.timer.database

import app.cash.sqldelight.db.SqlDriver
import com.mikeapp.timer.data.room.AppDatabase

// NativeDriverFactory.kt
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = AppDatabase.Schema,
            name = "app.db"
        )
    }
}