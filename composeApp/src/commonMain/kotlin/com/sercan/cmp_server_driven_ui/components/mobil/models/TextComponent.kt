package com.sercan.cmp_server_driven_ui.components.mobil.models

import com.sercan.cmp_server_driven_ui.components.mobil.enums.ComponentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TEXT")
data class TextComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.TEXT,
    override val position: Position = Position(),
    override val style: ComponentStyle? = null,
    val text: String
) : UiComponent()