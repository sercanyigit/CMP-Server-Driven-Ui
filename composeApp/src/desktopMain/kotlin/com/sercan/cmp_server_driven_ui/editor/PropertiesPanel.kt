package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.*

@Composable
fun PropertiesPanel(
    selectedComponent: UiComponent?,
    onComponentUpdated: (UiComponent) -> Unit
) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        if (selectedComponent == null) {
            Text("Hiçbir öğe seçilmedi")
            return
        }

        Text("Özellikler", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(16.dp))

        // Genel özellikler
        TextField(
            value = selectedComponent.id,
            onValueChange = {},
            label = { Text("ID") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(Modifier.height(8.dp))

        // Pozisyon özellikleri
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = selectedComponent.position.x.toString(),
                onValueChange = { str ->
                    str.toIntOrNull()?.let { x ->
                        onComponentUpdated(updateComponentPosition(selectedComponent) { 
                            it.copy(x = x) 
                        })
                    }
                },
                label = { Text("X") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = selectedComponent.position.y.toString(),
                onValueChange = { str ->
                    str.toIntOrNull()?.let { y ->
                        onComponentUpdated(updateComponentPosition(selectedComponent) { 
                            it.copy(y = y) 
                        })
                    }
                },
                label = { Text("Y") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        // Bileşene özel özellikler
        when (selectedComponent) {
            is TextComponent -> TextComponentProperties(selectedComponent, onComponentUpdated)
            is ButtonComponent -> ButtonComponentProperties(selectedComponent, onComponentUpdated)
            is TextFieldComponent -> TextFieldComponentProperties(selectedComponent, onComponentUpdated)
            is CheckboxComponent -> CheckboxComponentProperties(selectedComponent, onComponentUpdated)
            is RadioButtonComponent -> RadioButtonComponentProperties(selectedComponent, onComponentUpdated)
            is DropdownComponent -> DropdownComponentProperties(selectedComponent, onComponentUpdated)
            is SwitchComponent -> SwitchComponentProperties(selectedComponent, onComponentUpdated)
        }
    }
}

@Composable
private fun TextComponentProperties(
    component: TextComponent,
    onComponentUpdated: (UiComponent) -> Unit
) {
    TextField(
        value = component.text,
        onValueChange = { 
            onComponentUpdated(component.copy(text = it))
        },
        label = { Text("Metin") },
        modifier = Modifier.fillMaxWidth()
    )
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
    Column {
        TextField(
            value = component.hint,
            onValueChange = { 
                onComponentUpdated(component.copy(hint = it))
            },
            label = { Text("İpucu") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(Modifier.height(8.dp))
        
        TextField(
            value = component.label ?: "",
            onValueChange = { 
                onComponentUpdated(component.copy(label = it))
            },
            label = { Text("Etiket") },
            modifier = Modifier.fillMaxWidth()
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