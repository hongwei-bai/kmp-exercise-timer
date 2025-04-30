package com.example.kmpdemo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.kmpdemo.theme.GreenTheme
import com.example.kmpdemo.ui.HomeScreen
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