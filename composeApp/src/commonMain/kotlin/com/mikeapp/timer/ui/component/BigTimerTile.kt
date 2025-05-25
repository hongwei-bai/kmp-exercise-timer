package com.mikeapp.timer.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikeapp.timer.ui.theme.JetBrainsMonoFontFamily
import com.mikeapp.timer.ui.util.formatMillisTo24hTime
import com.mikeapp.timer.ui.util.getAmPmFromTimeMs

@Composable
fun BigTimerTile(
    currentTimeLong: Long,
    modifier: Modifier = Modifier
) {
    val timeString = formatMillisTo24hTime(currentTimeLong)
    val amOrPm = getAmPmFromTimeMs(currentTimeLong)
    Row(modifier = modifier) {
        Text(
            text = timeString,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = JetBrainsMonoFontFamily()
        )

        Text(
            text = amOrPm,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = JetBrainsMonoFontFamily(),
            modifier = Modifier
                .offset(y = if (amOrPm == "AM") 16.dp else 50.dp)
                .padding(start = 4.dp)
        )
    }
}
