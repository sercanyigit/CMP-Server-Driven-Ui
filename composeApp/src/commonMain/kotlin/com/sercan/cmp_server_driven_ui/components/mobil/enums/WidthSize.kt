package com.sercan.cmp_server_driven_ui.components.mobil.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class WidthSize {
    @SerialName("FIXED")
    FIXED,

    @SerialName("HALF")
    HALF,

    @SerialName("FULL")
    FULL
}