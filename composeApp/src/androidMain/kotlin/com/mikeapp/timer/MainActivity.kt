package com.mikeapp.timer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mikeapp.timer.permission.AndroidPermissionHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!AndroidPermissionHelper.hasNotificationPermission(this)) {
            AndroidPermissionHelper.requestNotificationPermission(this)
        }

        if (!AndroidPermissionHelper.hasExactAlarmPermission(this)) {
            AndroidPermissionHelper.promptExactAlarmPermission(this)
        }

        setContent {
            App()
        }
    }

    override fun onResume() {
        super.onResume()
        val dbPath = this.getDatabasePath("app.db").absolutePath
        Log.d("SQLDelight", "DB path: $dbPath")
    }

    override fun onPause() {
        Log.d(">>>>>", "App onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(">>>>>", "App onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(">>>>>", "App onDestroy")
        super.onDestroy()
    }
}