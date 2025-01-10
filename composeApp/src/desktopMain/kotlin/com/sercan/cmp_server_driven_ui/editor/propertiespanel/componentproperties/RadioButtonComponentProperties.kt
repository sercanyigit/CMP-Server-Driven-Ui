package com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent

@Composable
fun RadioButtonComponentProperties(
    component: RadioButtonComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    var newOption by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Seçili öğe
        Text("Seçenekler", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            component.options.forEach { option ->
                FilterChip(
                    selected = component.selectedOption == option,
                    onClick = {
                        onComponentUpdated(component.copy(selectedOption = option))
                    },
                    label = { Text(option) }
                )
            }
        }

        // Seçenekleri düzenleme
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newOption,
                onValueChange = { newOption = it },
                label = { Text("Yeni Seçenek") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newOption.isNotEmpty()) {
                        val updatedOptions = component.options + newOption
                        onComponentUpdated(component.copy(
                            options = if (updatedOptions.size <= 2) updatedOptions else component.options
                        ))
                        newOption = ""
                    }
                },
                enabled = newOption.isNotEmpty() && component.options.size < 2
            ) {
                Icon(Icons.Default.Check, contentDescription = "Ekle")
            }
        }

        // Mevcut seçenekler
        if (component.options.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                component.options.forEach { option ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                val updatedOptions = component.options - option
                                onComponentUpdated(component.copy(
                                    options = updatedOptions,
                                    selectedOption = if (component.selectedOption == option) null else component.selectedOption
                                ))
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil")
                        }
                    }
                }
            }
        }
    }
}