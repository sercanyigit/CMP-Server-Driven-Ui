package com.sercan.cmp_server_driven_ui.components.mobil.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class HorizontalAlignment {
    @SerialName("START")
    START,    // Sol

    @SerialName("CENTER")
    CENTER,   // Orta

    @SerialName("END")
    END       // Sağ
}