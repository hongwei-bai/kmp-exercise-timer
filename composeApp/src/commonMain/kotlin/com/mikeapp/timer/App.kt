package com.mikeapp.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikeapp.timer.ui.HomeScreen
import com.mikeapp.timer.ui.theme.MyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MyAppTheme  {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {
                HomeScreen()
            }
        }
    }
}