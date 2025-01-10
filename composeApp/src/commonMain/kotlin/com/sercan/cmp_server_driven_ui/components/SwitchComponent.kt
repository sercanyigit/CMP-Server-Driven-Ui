package com.sercan.cmp_server_driven_ui.components

import com.sercan.cmp_server_driven_ui.components.enums.ComponentType
import com.sercan.cmp_server_driven_ui.components.models.ComponentStyle
import com.sercan.cmp_server_driven_ui.components.models.Position
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("SWITCH")
data class SwitchComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.SWITCH,
    override val position: Position = Position(),
    override val style: ComponentStyle? = null,
    val label: String,
    val isChecked: Boolean = false
) : UiComponent()