package com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent

@Composable
fun TextFieldComponentProperties(
    component: TextFieldComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        var localValue by remember(component.id) { mutableStateOf(component.value) }
        var localHint by remember(component.id) { mutableStateOf(component.hint) }
        var localLabel by remember(component.id) { mutableStateOf(component.label ?: "") }

        // Value değiştiğinde state'i güncelle
        LaunchedEffect(component.value) {
            localValue = component.value
        }

        OutlinedTextField(
            value = localValue,
            onValueChange = { newValue ->
                localValue = newValue
                onComponentUpdated(component.copy(value = newValue))
            },
            label = { Text("Value") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = localHint,
            onValueChange = { newHint ->
                localHint = newHint
                onComponentUpdated(component.copy(hint = newHint))
            },
            label = { Text("Hint") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = localLabel,
            onValueChange = { newLabel ->
                localLabel = newLabel
                onComponentUpdated(component.copy(label = newLabel))
            },
            label = { Text("Label") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}