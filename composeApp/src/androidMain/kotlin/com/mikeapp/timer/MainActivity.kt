package com.mikeapp.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}