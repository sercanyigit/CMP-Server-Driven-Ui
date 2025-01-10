package com.sercan.cmp_server_driven_ui.components.desktop

import androidx.compose.runtime.Composable
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopButtonComponentRenderer
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopCheckboxComponentRender
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopDropdownComponentRender
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopRadioButtonComponentRender
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopSwitchComponentRender
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopTextComponentRenderer
import com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents.DesktopTextFieldComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.CheckboxComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.DropdownComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.SwitchComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent

@Composable
fun DesktopScreenRenderer(
    component: UiComponent,
    onStateChanged: (UiComponent) -> Unit
) {
    when (component) {
        is ButtonComponent -> {
            DesktopButtonComponentRenderer(component)
        }
        is CheckboxComponent -> {
            DesktopCheckboxComponentRender(component, onStateChanged)
        }
        is SwitchComponent -> {
            DesktopSwitchComponentRender(component, onStateChanged)
        }
        is RadioButtonComponent -> {
            DesktopRadioButtonComponentRender(component, onStateChanged)
        }
        is DropdownComponent -> {
            DesktopDropdownComponentRender(component, onStateChanged)
        }
        is TextComponent -> {
            DesktopTextComponentRenderer(component)
        }
        is TextFieldComponent -> {
            DesktopTextFieldComponentRender(component)
        }
    }
}