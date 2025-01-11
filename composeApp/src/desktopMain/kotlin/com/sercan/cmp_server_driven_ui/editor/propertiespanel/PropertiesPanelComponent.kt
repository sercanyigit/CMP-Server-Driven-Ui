package com.sercan.cmp_server_driven_ui.editor.propertiespanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.ButtonComponentProperties
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.CheckboxComponentProperties
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.DropdownComponentProperties
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.RadioButtonComponentProperties
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.SwitchComponentProperties
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.TextComponentProperties
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.componentproperties.TextFieldComponentProperties
import com.sercan.cmp_server_driven_ui.util.ColorUtil

@Composable
fun PropertiesPanelComponent(
    selectedComponent: UiComponent?,
    onComponentUpdated: (UiComponent) -> Unit,
    onClearRequest: () -> Unit,
    onSaveRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(380.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            "Component Özellikleri",
            style = MaterialTheme.typography.titleMedium
        )

        if (selectedComponent != null) {
            // ID alanı
            OutlinedTextField(
                value = selectedComponent.id,
                onValueChange = { },
                label = { Text("ID") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider()

            // Component özellikleri
            when (selectedComponent) {
                is TextComponent -> TextComponentProperties(selectedComponent, onComponentUpdated)
                is TextFieldComponent -> TextFieldComponentProperties(selectedComponent, onComponentUpdated)
                is CheckboxComponent -> CheckboxComponentProperties(selectedComponent, onComponentUpdated)
                is RadioButtonComponent -> RadioButtonComponentProperties(selectedComponent, onComponentUpdated)
                is DropdownComponent -> DropdownComponentProperties(selectedComponent, onComponentUpdated)
                is SwitchComponent -> SwitchComponentProperties(selectedComponent, onComponentUpdated)
                is ButtonComponent -> ButtonComponentProperties(selectedComponent, onComponentUpdated)
            }

            HorizontalDivider()

            // Stil özellikleri
            var localPosition by remember(selectedComponent.id) { mutableStateOf(selectedComponent.position) }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Boyut özellikleri
                Text("Boyut", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = localPosition.width.toString(),
                        onValueChange = { str ->
                            str.toIntOrNull()?.let { width ->
                                val newPosition = localPosition.copy(width = width)
                                localPosition = newPosition
                                onComponentUpdated(
                                    when (selectedComponent) {
                                        is TextComponent -> selectedComponent.copy(position = newPosition)
                                        is ButtonComponent -> selectedComponent.copy(position = newPosition)
                                        is TextFieldComponent -> selectedComponent.copy(position = newPosition)
                                        is CheckboxComponent -> selectedComponent.copy(position = newPosition)
                                        is RadioButtonComponent -> selectedComponent.copy(position = newPosition)
                                        is DropdownComponent -> selectedComponent.copy(position = newPosition)
                                        is SwitchComponent -> selectedComponent.copy(position = newPosition)
                                    }
                                )
                            }
                        },
                        label = { Text("Genişlik") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = localPosition.height.toString(),
                        onValueChange = { str ->
                            str.toIntOrNull()?.let { height ->
                                val newPosition = localPosition.copy(height = height)
                                localPosition = newPosition
                                onComponentUpdated(
                                    when (selectedComponent) {
                                        is TextComponent -> selectedComponent.copy(position = newPosition)
                                        is ButtonComponent -> selectedComponent.copy(position = newPosition)
                                        is TextFieldComponent -> selectedComponent.copy(position = newPosition)
                                        is CheckboxComponent -> selectedComponent.copy(position = newPosition)
                                        is RadioButtonComponent -> selectedComponent.copy(position = newPosition)
                                        is DropdownComponent -> selectedComponent.copy(position = newPosition)
                                        is SwitchComponent -> selectedComponent.copy(position = newPosition)
                                    }
                                )
                            }
                        },
                        label = { Text("Yükseklik") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Alt butonlar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onClearRequest,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ColorUtil.Primary
                )
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Tümünü Sil")
                Spacer(Modifier.width(8.dp))
                Text("Tümünü Sil")
            }

            Button(
                onClick = onSaveRequest,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Kaydet")
                Spacer(Modifier.width(8.dp))
                Text("Kaydet")
            }
        }
    }
}











