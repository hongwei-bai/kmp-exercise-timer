package com.mikeapp.timer.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

// DesktopDriverFactory.kt
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        // In-memory db
//        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
//            AppDatabase.Schema.create(it)
//        }

        // persistent db
        val dbDir = File(System.getProperty("user.home"), ".mytimerapp")
        if (!dbDir.exists()) dbDir.mkdirs()

        val dbFile = File(dbDir, "timer.db")
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")

        // ✅ Only create schema if the database file doesn't already exist
        if (!dbFile.exists()) {
            AppDatabase.Schema.create(driver)
        }

        return driver
    }
}