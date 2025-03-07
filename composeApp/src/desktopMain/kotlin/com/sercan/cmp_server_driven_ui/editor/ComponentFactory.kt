package com.sercan.cmp_server_driven_ui.editor

import com.sercan.cmp_server_driven_ui.components.mobil.enums.ComponentType
import com.sercan.cmp_server_driven_ui.components.mobil.enums.HorizontalAlignment
import com.sercan.cmp_server_driven_ui.components.mobil.enums.WidthSize
import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.CheckboxComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.DropdownComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.Position
import com.sercan.cmp_server_driven_ui.components.mobil.models.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.SwitchComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import java.util.UUID

object ComponentFactory {
    private fun generateComponentId(componentName: String): String {
        return "${componentName}_${UUID.randomUUID().toString().take(12)}"
    }

    fun createComponent(type: ComponentType, index: Int): UiComponent {
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
                id = generateComponentId("text"),
                position = defaultPosition,
                text = "Metin"
            )
            ComponentType.BUTTON -> ButtonComponent(
                id = generateComponentId("button"),
                position = defaultPosition,
                text = "Buton"
            )
            ComponentType.TEXT_FIELD -> TextFieldComponent(
                id = generateComponentId("textfield"),
                hint = "Hint",
                label = "Label",
                value = "Value",
                position = defaultPosition
            )
            ComponentType.CHECKBOX -> CheckboxComponent(
                id = generateComponentId("checkbox"),
                position = defaultPosition,
                options = listOf("Seçenek 1", "Seçenek 2")
            )
            ComponentType.RADIO_BUTTON -> RadioButtonComponent(
                id = generateComponentId("radiobutton"),
                position = defaultPosition,
                options = listOf("Seçenek 1", "Seçenek 2")
            )
            ComponentType.DROPDOWN -> DropdownComponent(
                id = generateComponentId("dropdown"),
                label = "Dropdown",
                options = listOf("Seçenek 1", "Seçenek 2", "Seçenek 3"),
                position = defaultPosition
            )
            ComponentType.SWITCH -> SwitchComponent(
                id = generateComponentId("switch"),
                label = "Switch",
                isChecked = false,
                position = defaultPosition
            )
        }
    }
}