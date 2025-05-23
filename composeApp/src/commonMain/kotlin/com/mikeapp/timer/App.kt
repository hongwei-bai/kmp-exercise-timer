package com.mikeapp.timer

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.mikeapp.timer.ui.HomeScreen
import com.mikeapp.timer.ui.theme.MyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MyAppTheme  {
            HomeScreen()
        }
    }
}