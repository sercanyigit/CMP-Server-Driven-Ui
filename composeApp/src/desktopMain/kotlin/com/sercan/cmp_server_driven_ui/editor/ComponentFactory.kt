package com.sercan.cmp_server_driven_ui.editor

import com.sercan.cmp_server_driven_ui.model.*

object ComponentFactory {
    fun createComponent(type: ComponentType, index: Int): UiComponent {
        val id = "${type.name.lowercase()}_$index"
        val defaultPosition = Position(
            x = 0,
            y = 0,
            width = 328,
            height = 60,
            widthSize = WidthSize.FULL,
            alignment = HorizontalAlignment.CENTER
        )

        return when (type) {
            ComponentType.TEXT -> TextComponent(
                id = id,
                position = defaultPosition,
                text = "Metin"
            )
            ComponentType.BUTTON -> ButtonComponent(
                id = id,
                position = defaultPosition,
                text = "Buton"
            )
            ComponentType.TEXT_FIELD -> TextFieldComponent(
                id =id,
                hint = "Hint",
                label = "Label",
                value = "Value",
                position = defaultPosition
            )
            ComponentType.CHECKBOX -> CheckboxComponent(
                id = id,
                position = defaultPosition,
                label = "Checkbox"
            )
            ComponentType.RADIO_BUTTON -> RadioButtonComponent(
                id = id,
                position = defaultPosition,
                label = "Radio buton",
                group = "grup1"
            )
            ComponentType.DROPDOWN -> DropdownComponent(
                id = id,
                label = "Dropdown",
                options = listOf("Seçenek 1", "Seçenek 2", "Seçenek 3"),
                position = defaultPosition
            )
            ComponentType.SWITCH -> SwitchComponent(
                id = "switch_$index",
                label = "Switch",
                isChecked = false,
                position = defaultPosition
            )
        }
    }
} 