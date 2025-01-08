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
}

@Serializable
data class Position(
    val x: Int,
    val y: Int,
    val width: Int = 100,
    val height: Int = 40
)

@Serializable
data class ComponentStyle(
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val fontSize: Int? = null,
    val padding: Int? = null,
    val cornerRadius: Int? = null
)

@Serializable
enum class ComponentType {
    @SerialName("TEXT")
    TEXT,
    @SerialName("BUTTON")
    BUTTON,
    @SerialName("TEXT_FIELD")
    TEXT_FIELD
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
    val label: String? = null
) : UiComponent()