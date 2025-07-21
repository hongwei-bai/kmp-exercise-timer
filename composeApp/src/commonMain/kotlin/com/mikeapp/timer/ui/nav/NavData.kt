package com.mikeapp.timer.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.mikeapp.timer.data.model.TimerType
import com.mikeapp.timer.ui.component.DrawerNavItem

object NavData {
    const val APP_NAME = "Melon Timer"

    val navItemsMain = listOf(
        DrawerNavItem(TimerType.MINIMALISM.displayName, Icons.Default.AccessTime),
        DrawerNavItem(TimerType.GYM_REST.displayName, Icons.Default.FitnessCenter),
        DrawerNavItem(TimerType.ALARM.displayName, Icons.Default.Alarm)
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