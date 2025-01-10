package com.sercan.cmp_server_driven_ui.components.mobil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.CheckboxComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.DropdownComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.SwitchComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.ButtonComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.CheckboxComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.DropdownComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.RadioButtonComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.SwitchComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.TextComponentRender
import com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents.TextFieldComponentRender

@Composable
fun MobileScreenRenderer(
    components: List<UiComponent>,
    onComponentStateChanged: (UiComponent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        components.forEach { component ->
            when (component) {
                is TextComponent -> {
                    TextComponentRender(component)
                }
                is ButtonComponent -> {
                    ButtonComponentRender(component)
                }
                is TextFieldComponent -> {
                    TextFieldComponentRender(component, onComponentStateChanged)
                }
                is CheckboxComponent -> {
                    CheckboxComponentRender(component, onComponentStateChanged)
                }
                is RadioButtonComponent -> {
                    RadioButtonComponentRender(component, onComponentStateChanged)
                }
                is DropdownComponent -> {
                    DropdownComponentRender(component, onComponentStateChanged)
                }
                is SwitchComponent -> {
                    SwitchComponentRender(component, onComponentStateChanged)
                }
            }
        }
    }
}