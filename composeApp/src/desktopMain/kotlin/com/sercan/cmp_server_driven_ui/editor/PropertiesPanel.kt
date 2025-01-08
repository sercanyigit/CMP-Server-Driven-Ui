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
            .background(Color.LightGray)
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

        // Pozisyon
        Text("Pozisyon", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var position = selectedComponent.position
            
            TextField(
                value = position.x.toString(),
                onValueChange = { 
                    position = position.copy(x = it.toIntOrNull() ?: position.x)
                    updateComponent(selectedComponent, position, onComponentUpdated)
                },
                label = { Text("X") },
                modifier = Modifier.weight(1f)
            )
            
            TextField(
                value = position.y.toString(),
                onValueChange = { 
                    position = position.copy(y = it.toIntOrNull() ?: position.y)
                    updateComponent(selectedComponent, position, onComponentUpdated)
                },
                label = { Text("Y") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var position = selectedComponent.position
            
            TextField(
                value = position.width.toString(),
                onValueChange = { 
                    position = position.copy(width = it.toIntOrNull() ?: position.width)
                    updateComponent(selectedComponent, position, onComponentUpdated)
                },
                label = { Text("Genişlik") },
                modifier = Modifier.weight(1f)
            )
            
            TextField(
                value = position.height.toString(),
                onValueChange = { 
                    position = position.copy(height = it.toIntOrNull() ?: position.height)
                    updateComponent(selectedComponent, position, onComponentUpdated)
                },
                label = { Text("Yükseklik") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Komponent spesifik özellikler
        when (selectedComponent) {
            is TextComponent -> TextComponentProperties(selectedComponent, onComponentUpdated)
            is ButtonComponent -> ButtonComponentProperties(selectedComponent, onComponentUpdated)
            is TextFieldComponent -> TextFieldComponentProperties(selectedComponent, onComponentUpdated)
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

private fun updateComponent(
    component: UiComponent,
    newPosition: Position,
    onComponentUpdated: (UiComponent) -> Unit
) {
    val updated = when (component) {
        is TextComponent -> component.copy(position = newPosition)
        is ButtonComponent -> component.copy(position = newPosition)
        is TextFieldComponent -> component.copy(position = newPosition)
    }
    onComponentUpdated(updated)
} 