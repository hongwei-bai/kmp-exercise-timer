package com.mikeapp.timer.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mikeapp.timer.ui.nav.NavData.APP_NAME
import com.mikeapp.timer.ui.nav.NavData.configItems
import com.mikeapp.timer.ui.nav.NavData.footerItems
import com.mikeapp.timer.ui.nav.NavData.navItemsMain

@Composable
fun AppDrawerNavList(
    onItemSelected: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = APP_NAME,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Main Nav Items ---
        navItemsMain.forEach { item ->
            DrawerItem(item, onItemSelected)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

        // --- Config Items ---
        configItems.forEach { item ->
            DrawerItem(item, onItemSelected)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

        // --- Footer Items ---
        footerItems.forEach { item ->
            DrawerItem(item, onItemSelected)
        }
    }
}

data class DrawerNavItem(val label: String, val icon: ImageVector)

@Composable
private fun DrawerItem(item: DrawerNavItem, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item.label) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.label,
            style = MaterialTheme.typography.subtitle1
        )
    }
}
