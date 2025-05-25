package com.mikeapp.timer

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            applySystemBarStyle()
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

    fun applySystemBarStyle() {
        val isDarkTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.statusBarColor = if (isDarkTheme) Color.BLACK else Color.WHITE
            window.navigationBarColor = if (isDarkTheme) Color.BLACK else Color.WHITE

            window.insetsController?.setSystemBarsAppearance(
                if (!isDarkTheme)
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                else
                    0, // Clear light appearance = default light icons
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = when {
                !isDarkTheme -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                else -> 0 // light icons
            }

            @Suppress("DEPRECATION")
            window.statusBarColor = if (isDarkTheme) Color.BLACK else Color.WHITE
            @Suppress("DEPRECATION")
            window.navigationBarColor = if (isDarkTheme) Color.BLACK else Color.WHITE
        }
    }

}