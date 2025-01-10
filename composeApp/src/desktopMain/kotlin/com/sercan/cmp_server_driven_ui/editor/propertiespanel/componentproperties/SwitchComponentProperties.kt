package com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sercan.cmp_server_driven_ui.components.mobil.models.SwitchComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent


@Composable
fun SwitchComponentProperties(
    component: SwitchComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column {
        TextField(
            value = component.label,
            onValueChange = { onComponentUpdated(component.copy(label = it)) },
            label = { Text("Etiket") },
            modifier = Modifier.fillMaxWidth()
        )
        Switch(
            checked = component.isChecked,
            onCheckedChange = { onComponentUpdated(component.copy(isChecked = it)) }
        )
    }
}