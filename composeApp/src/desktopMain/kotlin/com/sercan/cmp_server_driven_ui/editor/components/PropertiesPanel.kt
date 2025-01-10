package com.sercan.cmp_server_driven_ui.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.ButtonComponent
import com.sercan.cmp_server_driven_ui.model.CheckboxComponent
import com.sercan.cmp_server_driven_ui.model.DropdownComponent
import com.sercan.cmp_server_driven_ui.model.HorizontalAlignment
import com.sercan.cmp_server_driven_ui.model.Position
import com.sercan.cmp_server_driven_ui.model.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.model.SwitchComponent
import com.sercan.cmp_server_driven_ui.model.TextComponent
import com.sercan.cmp_server_driven_ui.model.TextFieldComponent
import com.sercan.cmp_server_driven_ui.model.UiComponent
import com.sercan.cmp_server_driven_ui.model.WidthSize

@Composable
fun PropertiesPanel(
    selectedComponent: UiComponent?,
    onComponentUpdated: (UiComponent) -> Unit,
    onClearRequest: () -> Unit,
    onSaveRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(350.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            "Özellikler",
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

            Divider()

            // Component özellikleri
            when (selectedComponent) {
                is TextComponent -> TextComponentProperties(selectedComponent, onComponentUpdated)
                is ButtonComponent -> ButtonComponentProperties(selectedComponent, onComponentUpdated)
                is TextFieldComponent -> TextFieldComponentProperties(selectedComponent, onComponentUpdated)
                is CheckboxComponent -> CheckboxComponentProperties(selectedComponent, onComponentUpdated)
                is RadioButtonComponent -> RadioButtonComponentProperties(selectedComponent, onComponentUpdated)
                is DropdownComponent -> DropdownComponentProperties(selectedComponent, onComponentUpdated)
                is SwitchComponent -> SwitchComponentProperties(selectedComponent, onComponentUpdated)
            }

            Divider()

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

                Spacer(modifier = Modifier.height(16.dp))

                // Genişlik türü seçimi
                Text("Genişlik Türü", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WidthSize.values().forEach { widthSize ->
                        FilterChip(
                            selected = localPosition.widthSize == widthSize,
                            onClick = {
                                val newPosition = localPosition.copy(widthSize = widthSize)
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
                            },
                            label = { Text(widthSize.name) }
                        )
                    }
                }

                // Hizalama seçimi
                Text("Hizalama", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HorizontalAlignment.values().forEach { alignment ->
                        FilterChip(
                            selected = localPosition.alignment == alignment,
                            onClick = {
                                val newPosition = localPosition.copy(alignment = alignment)
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
                            },
                            label = { Text(alignment.name) }
                        )
                    }
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
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Temizle")
                Spacer(Modifier.width(8.dp))
                Text("Temizle")
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

@Composable
private fun TextComponentProperties(
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

@Composable
private fun ButtonComponentProperties(
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

@Composable
private fun TextFieldComponentProperties(
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

@Composable
private fun CheckboxComponentProperties(
    component: CheckboxComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column {
        TextField(
            value = component.label,
            onValueChange = { onComponentUpdated(component.copy(label = it)) },
            label = { Text("Etiket") },
            modifier = Modifier.fillMaxWidth()
        )
        Checkbox(
            checked = component.isChecked,
            onCheckedChange = { onComponentUpdated(component.copy(isChecked = it)) }
        )
    }
}

@Composable
private fun RadioButtonComponentProperties(
    component: RadioButtonComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column {
        TextField(
            value = component.label,
            onValueChange = { onComponentUpdated(component.copy(label = it)) },
            label = { Text("Etiket") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = component.group,
            onValueChange = { onComponentUpdated(component.copy(group = it)) },
            label = { Text("Grup") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DropdownComponentProperties(
    component: DropdownComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column {
        TextField(
            value = component.label,
            onValueChange = { onComponentUpdated(component.copy(label = it)) },
            label = { Text("Etiket") },
            modifier = Modifier.fillMaxWidth()
        )
        // Seçenekleri düzenleme alanı eklenebilir
    }
}

@Composable
private fun SwitchComponentProperties(
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

private fun updateComponentPosition(
    component: UiComponent,
    update: (Position) -> Position
): UiComponent {
    return when (component) {
        is TextComponent -> component.copy(position = update(component.position))
        is ButtonComponent -> component.copy(position = update(component.position))
        is TextFieldComponent -> component.copy(position = update(component.position))
        is CheckboxComponent -> component.copy(position = update(component.position))
        is RadioButtonComponent -> component.copy(position = update(component.position))
        is DropdownComponent -> component.copy(position = update(component.position))
        is SwitchComponent -> component.copy(position = update(component.position))
    }
} 