package com.sercan.cmp_server_driven_ui.components

import com.sercan.cmp_server_driven_ui.components.enums.ComponentType
import com.sercan.cmp_server_driven_ui.components.models.ComponentStyle
import com.sercan.cmp_server_driven_ui.components.models.Position
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class UiComponent {
    abstract val id: String
    @SerialName("componentType")
    abstract val type: ComponentType
    abstract val position: Position
    abstract val style: ComponentStyle?

    fun updatePosition(newPosition: Position): UiComponent {
        return when (this) {
            is TextComponent -> copy(position = newPosition)
            is ButtonComponent -> copy(position = newPosition)
            is TextFieldComponent -> copy(position = newPosition)
            is CheckboxComponent -> copy(position = newPosition)
            is RadioButtonComponent -> copy(position = newPosition)
            is DropdownComponent -> copy(position = newPosition)
            is SwitchComponent -> copy(position = newPosition)
        }
    }
}