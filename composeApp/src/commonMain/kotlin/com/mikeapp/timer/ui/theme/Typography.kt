package com.mikeapp.timer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kmp_exercise_timer.composeapp.generated.resources.Res
import kmp_exercise_timer.composeapp.generated.resources.exo2_medium
import kmp_exercise_timer.composeapp.generated.resources.jetbrainsmono_medium
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Exo2FontFamily() = FontFamily(
    Font(Res.font.exo2_medium, weight = FontWeight.Light),
    Font(Res.font.exo2_medium, weight = FontWeight.Normal),
    Font(Res.font.exo2_medium, weight = FontWeight.Medium),
    Font(Res.font.exo2_medium, weight = FontWeight.SemiBold),
    Font(Res.font.exo2_medium, weight = FontWeight.Bold)
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun JetBrainsMonoFontFamily() = FontFamily(
    Font(Res.font.jetbrainsmono_medium, weight = FontWeight.Light),
    Font(Res.font.jetbrainsmono_medium, weight = FontWeight.Normal),
    Font(Res.font.jetbrainsmono_medium, weight = FontWeight.Medium),
    Font(Res.font.jetbrainsmono_medium, weight = FontWeight.SemiBold),
    Font(Res.font.jetbrainsmono_medium, weight = FontWeight.Bold)
)