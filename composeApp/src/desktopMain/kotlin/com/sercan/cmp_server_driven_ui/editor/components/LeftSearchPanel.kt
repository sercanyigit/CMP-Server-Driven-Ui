package com.sercan.cmp_server_driven_ui.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.ComponentType

@Composable
fun SearchLeftPanel(
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
                    ComponentType.TEXT -> "Label"
                    ComponentType.BUTTON -> "Buton"
                    ComponentType.TEXT_FIELD -> "Text Field"
                    ComponentType.CHECKBOX -> "Checkbox"
                    ComponentType.RADIO_BUTTON -> "Radio buton"
                    ComponentType.DROPDOWN -> "Liste"
                    ComponentType.SWITCH -> "Switch"
                }.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .width(300.dp)
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
            placeholder = { Text("Component ara...") },
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