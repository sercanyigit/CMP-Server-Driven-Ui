package com.sercan.cmp_server_driven_ui.util

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val colorString = if (this.startsWith("#")) this.substring(1) else this
    return when (colorString.length) {
        6 -> Color(
            red = colorString.substring(0, 2).toInt(16) / 255f,
            green = colorString.substring(2, 4).toInt(16) / 255f,
            blue = colorString.substring(4, 6).toInt(16) / 255f,
            alpha = 1.0f
        )
        8 -> Color(
            red = colorString.substring(2, 4).toInt(16) / 255f,
            green = colorString.substring(4, 6).toInt(16) / 255f,
            blue = colorString.substring(6, 8).toInt(16) / 255f,
            alpha = colorString.substring(0, 2).toInt(16) / 255f
        )
        else -> Color.Unspecified
    }
}

object ColorUtil {
    val Primary = "#FF385C".toColor()
    val Secondary = "#FF385C".toColor()
    val TextPrimary = "#222222".toColor()
    val TextSecondary = "#717171".toColor()
    val Background = "#FFFFFF".toColor()
    val Border = "#E8E8E8".toColor()
    val Error = "#D63031".toColor()
    val Success = "#00B894".toColor()
    val Disabled = "#F5F5F5".toColor()
    val DisabledText = "#A1A1A1".toColor()
    val SelectedBackground = "#FFF1F3".toColor()
    val UnselectedBackground = "#FFFFFF".toColor()
}
