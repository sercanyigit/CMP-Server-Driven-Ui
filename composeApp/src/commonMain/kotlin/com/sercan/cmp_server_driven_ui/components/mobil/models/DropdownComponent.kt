package com.sercan.cmp_server_driven_ui.components.mobil.models

import com.sercan.cmp_server_driven_ui.components.mobil.enums.ComponentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("DROPDOWN")
data class DropdownComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.DROPDOWN,
    override val position: Position = Position(),
    override val style: ComponentStyle? = null,
    val label: String,
    val options: List<String>,
    val selectedOption: String? = null
) : UiComponent()