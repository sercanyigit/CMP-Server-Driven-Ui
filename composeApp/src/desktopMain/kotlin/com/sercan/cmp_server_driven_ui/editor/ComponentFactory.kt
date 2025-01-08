package com.sercan.cmp_server_driven_ui.editor

import com.sercan.cmp_server_driven_ui.model.*

fun createComponent(type: ComponentType, index: Int): UiComponent {
    val id = "${type.name.lowercase()}_$index"
    val position = Position(10, 10)
    
    return when (type) {
        ComponentType.TEXT -> TextComponent(
            id = id,
            position = position,
            text = "Metin"
        )
        ComponentType.BUTTON -> ButtonComponent(
            id = id,
            position = position,
            text = "Buton"
        )
        ComponentType.TEXT_FIELD -> TextFieldComponent(
            id = id,
            position = position,
            hint = "İpucu metni",
            label = "Etiket"
        )
        else -> TextComponent(
            id = id,
            position = position,
            text = "Desteklenmeyen tip"
        )
    }
} 