package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.ButtonComponent
import com.sercan.cmp_server_driven_ui.model.CheckboxComponent
import com.sercan.cmp_server_driven_ui.model.DropdownComponent
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
        Text("Konum", style = MaterialTheme.typography.labelMedium)
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

        Spacer(Modifier.height(16.dp))

        // Genişlik türü seçici
        WidthSizeSelector(
            currentSize = selectedComponent.position.widthSize,
            onSizeSelected = { newSize ->
                onComponentUpdated(updateComponentPosition(selectedComponent) { 
                    it.copy(widthSize = newSize) 
                })
            }
        )

        Spacer(Modifier.height(16.dp))

        // Boyut özellikleri
        Text("Boyut", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = selectedComponent.position.width.toString(),
                onValueChange = { str ->
                    str.toIntOrNull()?.let { width ->
                        onComponentUpdated(updateComponentPosition(selectedComponent) { 
                            it.copy(width = width) 
                        })
                    }
                },
                label = { Text("Genişlik") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = selectedComponent.position.height.toString(),
                onValueChange = { str ->
                    str.toIntOrNull()?.let { height ->
                        onComponentUpdated(updateComponentPosition(selectedComponent) { 
                            it.copy(height = height) 
                        })
                    }
                },
                label = { Text("Yükseklik") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))
        Divider()
        Spacer(Modifier.height(16.dp))

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

        Spacer(Modifier.weight(1f))
        
        // Butonlar
        Button(
            onClick = onClearRequest,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Temizle",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Temizle")
        }

        Button(
            onClick = onSaveRequest,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Kaydet",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Kaydet")
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

@Composable
private fun WidthSizeSelector(
    currentSize: WidthSize,
    onSizeSelected: (WidthSize) -> Unit
) {
    Column {
        Text(
            "Genişlik Türü", 
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WidthSize.values().forEach { size ->
                OutlinedButton(
                    onClick = { onSizeSelected(size) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (size == currentSize) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (size == currentSize)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text(
                        when (size) {
                            WidthSize.FIXED -> "Sabit"
                            WidthSize.HALF -> "Yarım"
                            WidthSize.FULL -> "Tam"
                        },
                        color = if (size == currentSize)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
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