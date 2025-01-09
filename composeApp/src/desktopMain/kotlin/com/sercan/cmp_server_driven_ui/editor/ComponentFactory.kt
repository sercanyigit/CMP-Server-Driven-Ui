package com.sercan.cmp_server_driven_ui.editor

import com.sercan.cmp_server_driven_ui.model.*

object ComponentFactory {
    fun createComponent(type: ComponentType, index: Int): UiComponent {
        val id = "${type.name.lowercase()}_$index"
        val position = Position(
            x = 0,
            y = 0,
            width = when(type) {
                ComponentType.TEXT -> 328
                ComponentType.BUTTON -> 160
                ComponentType.TEXT_FIELD -> 328
                ComponentType.CHECKBOX -> 160
                ComponentType.RADIO_BUTTON -> 160
                ComponentType.DROPDOWN -> 328
                ComponentType.SWITCH -> 160
            },
            height = when(type) {
                ComponentType.TEXT -> 40
                ComponentType.BUTTON -> 48
                ComponentType.TEXT_FIELD -> 56
                ComponentType.CHECKBOX -> 40
                ComponentType.RADIO_BUTTON -> 40
                ComponentType.DROPDOWN -> 80
                ComponentType.SWITCH -> 40
            }
        )
        
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
            ComponentType.CHECKBOX -> CheckboxComponent(
                id = id,
                position = position,
                label = "Onay Kutusu"
            )
            ComponentType.RADIO_BUTTON -> RadioButtonComponent(
                id = id,
                position = position,
                label = "Seçenek",
                group = "grup1"
            )
            ComponentType.DROPDOWN -> DropdownComponent(
                id = id,
                position = position,
                label = "Açılır Liste",
                options = listOf("Seçenek 1", "Seçenek 2", "Seçenek 3")
            )
            ComponentType.SWITCH -> SwitchComponent(
                id = id,
                position = position,
                label = "Anahtar"
            )
        }
    }
} 