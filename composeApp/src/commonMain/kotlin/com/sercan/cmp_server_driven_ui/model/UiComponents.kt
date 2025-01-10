package com.sercan.cmp_server_driven_ui.model

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

@Serializable
enum class HorizontalAlignment {
    @SerialName("START")
    START,    // Sol
    
    @SerialName("CENTER")
    CENTER,   // Orta
    
    @SerialName("END")
    END       // Sağ
}

@Serializable
data class Position(
    val x: Int,
    val y: Int,
    val width: Int = 100,
    val height: Int = 40,
    val widthSize: WidthSize = WidthSize.FIXED,
    val alignment: HorizontalAlignment = HorizontalAlignment.START
)

@Serializable
enum class WidthSize {
    @SerialName("FIXED")
    FIXED,
    
    @SerialName("HALF")
    HALF,
    
    @SerialName("FULL")
    FULL
}

@Serializable
data class ComponentStyle(
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val fontSize: Int? = null,
    val padding: Int? = null,
    val cornerRadius: Int? = null,
    val horizontalPadding: Int? = null
)

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

@Serializable
@SerialName("TEXT")
data class TextComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.TEXT,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val text: String
) : UiComponent()

@Serializable
@SerialName("BUTTON")
data class ButtonComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.BUTTON,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val text: String
) : UiComponent()

@Serializable
@SerialName("TEXT_FIELD")
data class TextFieldComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.TEXT_FIELD,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val hint: String,
    val label: String? = null,
    val value: String = "Value"
) : UiComponent()

@Serializable
@SerialName("CHECKBOX")
data class CheckboxComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.CHECKBOX,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val label: String,
    val isChecked: Boolean = false
) : UiComponent()

@Serializable
@SerialName("RADIO_BUTTON")
data class RadioButtonComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.RADIO_BUTTON,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val label: String,
    val group: String,
    val isSelected: Boolean = false
) : UiComponent()

@Serializable
@SerialName("DROPDOWN")
data class DropdownComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.DROPDOWN,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val label: String,
    val options: List<String>,
    val selectedOption: String? = null
) : UiComponent()

@Serializable
@SerialName("SWITCH")
data class SwitchComponent(
    override val id: String,
    @SerialName("componentType")
    override val type: ComponentType = ComponentType.SWITCH,
    override val position: Position,
    override val style: ComponentStyle? = null,
    val label: String,
    val isChecked: Boolean = false
) : UiComponent()