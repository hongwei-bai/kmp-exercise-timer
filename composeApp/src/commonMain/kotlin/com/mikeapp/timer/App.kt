package com.mikeapp.timer

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.mikeapp.timer.domain.TimerUseCase
import com.mikeapp.timer.ui.HomeScreen
import com.mikeapp.timer.ui.theme.GreenTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(timerUseCase: TimerUseCase) {
    MaterialTheme {
        GreenTheme {
//        LightBlueTheme {
//        RedTheme {
//        AmberTheme {
//        IndigoTheme {
//        PinkTheme {
//        DeepPurpleTheme {
//        RedTheme {
            HomeScreen(timerUseCase)
        }
    }
}