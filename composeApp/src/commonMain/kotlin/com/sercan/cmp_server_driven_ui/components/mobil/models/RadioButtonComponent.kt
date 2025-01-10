package com.sercan.cmp_server_driven_ui.components.mobil.models

import com.sercan.cmp_server_driven_ui.components.mobil.enums.ComponentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("RADIO_BUTTON")
data class RadioButtonComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.RADIO_BUTTON,
    override val position: Position = Position(),
    override val style: ComponentStyle? = null,
    val options: List<String> = listOf("Seçenek 1", "Seçenek 2"),
    val selectedOption: String? = null
) : UiComponent()