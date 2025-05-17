package com.mikeapp.timer

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.mikeapp.timer.theme.GreenTheme
import com.mikeapp.timer.ui.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        GreenTheme {
//        LightBlueTheme {
//        RedTheme {
//        AmberTheme {
//        IndigoTheme {
//        PinkTheme {
//        DeepPurpleTheme {
//        RedTheme {
            HomeScreen()
        }
    }
}