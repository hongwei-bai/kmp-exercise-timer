package com.mikeapp.timer.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.mikeapp.timer.ui.component.DrawerNavItem

object NavData {
    val navItemsMain = listOf(
        DrawerNavItem("Timer", Icons.Default.AccessTime),
        DrawerNavItem("Gym Timer", Icons.Default.FitnessCenter),
        DrawerNavItem("Alarm", Icons.Default.Alarm)
    )

    val configItems = listOf(
        DrawerNavItem("Always-on display", Icons.Default.Visibility),
        DrawerNavItem("Must wake up", Icons.Default.Warning)
    )

    val footerItems = listOf(
        DrawerNavItem("Feedback", Icons.Default.Feedback),
        DrawerNavItem("About", Icons.Default.Info)
    )
}