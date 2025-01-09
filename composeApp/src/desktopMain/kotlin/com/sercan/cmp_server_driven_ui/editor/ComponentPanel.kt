package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.ComponentType

@Composable
fun ComponentPanel(
    onComponentSelected: (ComponentType) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val componentTypes = remember { ComponentType.values().toList() }
    val filteredComponents = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            componentTypes
        } else {
            componentTypes.filter { componentType -> 
                when (componentType) {
                    ComponentType.TEXT -> "Metin"
                    ComponentType.BUTTON -> "Buton"
                    ComponentType.TEXT_FIELD -> "Metin Alanı"
                    ComponentType.CHECKBOX -> "Onay Kutusu"
                    ComponentType.RADIO_BUTTON -> "Radyo Düğmesi"
                    ComponentType.DROPDOWN -> "Açılır Liste"
                    ComponentType.SWITCH -> "Anahtar"
                }.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Arama alanı
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Bileşen ara...") },
            leadingIcon = { Icon(Icons.Default.Search, "Ara") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        // Bileşen kategorileri
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredComponents) { componentType ->
                ComponentItem(
                    componentType = componentType,
                    onClick = { onComponentSelected(componentType) }
                )
            }
        }
    }
}

@Composable
private fun ComponentItem(
    componentType: ComponentType,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = when (componentType) {
                    ComponentType.TEXT -> Icons.Default.Add
                    ComponentType.BUTTON -> Icons.Default.Add
                    ComponentType.TEXT_FIELD -> Icons.Default.Add
                    ComponentType.CHECKBOX -> Icons.Default.Add
                    ComponentType.RADIO_BUTTON -> Icons.Default.Add
                    ComponentType.DROPDOWN -> Icons.Default.Add
                    ComponentType.SWITCH -> Icons.Default.Add
                },
                contentDescription = componentType.name,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = when (componentType) {
                    ComponentType.TEXT -> "Metin"
                    ComponentType.BUTTON -> "Buton"
                    ComponentType.TEXT_FIELD -> "Metin Alanı"
                    ComponentType.CHECKBOX -> "Onay Kutusu"
                    ComponentType.RADIO_BUTTON -> "Radyo Düğmesi"
                    ComponentType.DROPDOWN -> "Açılır Liste"
                    ComponentType.SWITCH -> "Anahtar"
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 