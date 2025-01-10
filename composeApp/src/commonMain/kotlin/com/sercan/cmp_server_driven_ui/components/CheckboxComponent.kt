package com.sercan.cmp_server_driven_ui.components

import com.sercan.cmp_server_driven_ui.components.enums.ComponentType
import com.sercan.cmp_server_driven_ui.components.models.ComponentStyle
import com.sercan.cmp_server_driven_ui.components.models.Position
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("CHECKBOX")
data class CheckboxComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.CHECKBOX,
    override val position: Position = Position(),
    override val style: ComponentStyle? = null,
    val options: List<String> = listOf("Seçenek 1", "Seçenek 2"),
    val selectedOptions: List<String> = emptyList()
) : UiComponent()