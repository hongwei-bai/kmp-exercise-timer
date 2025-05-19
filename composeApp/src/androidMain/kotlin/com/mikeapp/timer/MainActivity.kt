package com.mikeapp.timer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mikeapp.timer.domain.TimerUseCase
import com.mikeapp.timer.permission.AndroidPermissionHelper
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private val timerUseCase = get<TimerUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!AndroidPermissionHelper.hasNotificationPermission(this)) {
            AndroidPermissionHelper.requestNotificationPermission(this)
        }

        if (!AndroidPermissionHelper.hasExactAlarmPermission(this)) {
            AndroidPermissionHelper.promptExactAlarmPermission(this)
        }

        setContent {
            App(timerUseCase)
        }
    }

    override fun onResume() {
        super.onResume()
        val dbPath = this.getDatabasePath("app.db").absolutePath
        Log.d("SQLDelight", "DB path: $dbPath")
    }
}