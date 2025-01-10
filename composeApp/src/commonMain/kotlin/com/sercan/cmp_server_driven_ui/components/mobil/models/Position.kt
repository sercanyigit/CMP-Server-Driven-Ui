package com.sercan.cmp_server_driven_ui.components.mobil.models

import com.sercan.cmp_server_driven_ui.components.mobil.enums.HorizontalAlignment
import com.sercan.cmp_server_driven_ui.components.mobil.enums.WidthSize
import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val x: Int = 0,
    val y: Int = 0,
    val width: Int = 300,
    val height: Int = 60,
    val widthSize: WidthSize = WidthSize.FULL,
    val alignment: HorizontalAlignment = HorizontalAlignment.CENTER
)