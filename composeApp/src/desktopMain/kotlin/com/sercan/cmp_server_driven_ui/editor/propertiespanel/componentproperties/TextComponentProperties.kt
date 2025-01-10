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
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent


@Composable
fun TextComponentProperties(
    component: TextComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        var localText by remember(component.id) { mutableStateOf(component.text) }

        // Text değiştiğinde state'i güncelle
        LaunchedEffect(component.text) {
            localText = component.text
        }

        OutlinedTextField(
            value = localText,
            onValueChange = { newText ->
                localText = newText
                onComponentUpdated(component.copy(text = newText))
            },
            label = { Text("Text") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}