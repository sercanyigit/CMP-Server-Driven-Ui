package com.sercan.cmp_server_driven_ui.components.mobil.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ComponentType {
    @SerialName("TEXT")
    TEXT,
    @SerialName("BUTTON")
    BUTTON,
    @SerialName("TEXT_FIELD")
    TEXT_FIELD,
    @SerialName("CHECKBOX")
    CHECKBOX,
    @SerialName("RADIO_BUTTON")
    RADIO_BUTTON,
    @SerialName("DROPDOWN")
    DROPDOWN,
    @SerialName("SWITCH")
    SWITCH
}