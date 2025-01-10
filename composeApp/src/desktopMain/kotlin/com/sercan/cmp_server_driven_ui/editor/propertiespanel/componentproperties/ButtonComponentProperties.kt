package com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent

@Composable
fun ButtonComponentProperties(
    component: ButtonComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    TextField(
        value = component.text,
        onValueChange = {
            onComponentUpdated(component.copy(text = it))
        },
        label = { Text("Buton Metni") },
        modifier = Modifier.fillMaxWidth()
    )
}