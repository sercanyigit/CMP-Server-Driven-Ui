package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu

@Composable
fun ScreenRenderer(
    components: List<UiComponent>,
    onComponentStateChanged: (UiComponent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var currentRow = mutableListOf<UiComponent>()
        components.forEach { component ->
            if (currentRow.sumOf { it.position.width } + component.position.width > 360) {
                // Yeni satıra geç
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentRow.forEach { rowComponent ->
                        ComponentRenderer(
                            component = rowComponent,
                            onStateChanged = onComponentStateChanged
                        )
                    }
                }
                currentRow = mutableListOf(component)
            } else {
                currentRow.add(component)
            }
        }
        // Son satırı ekle
        if (currentRow.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentRow.forEach { rowComponent ->
                    ComponentRenderer(
                        component = rowComponent,
                        onStateChanged = onComponentStateChanged
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentRenderer(
    component: UiComponent,
    onStateChanged: (UiComponent) -> Unit
) {
    Box(
        modifier = Modifier
            .width(component.position.width.dp)
            .height(component.position.height.dp)
    ) {
        when (component) {
            is TextComponent -> component.style?.toTextStyle()?.let {
                Text(
                    text = component.text,
                    style = it,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is ButtonComponent -> Button(
                onClick = {},
                modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth()
            ) {
                Text(component.text)
            }
            is TextFieldComponent -> {
                var text by remember(component.id) { mutableStateOf(component.hint) }
                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                        onStateChanged(component.copy(hint = newText))
                    },
                    label = { Text(component.label ?: "") },
                    placeholder = { Text(component.hint) },
                    modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth()
                )
            }
            is CheckboxComponent -> {
                Row(
                    modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = component.isChecked,
                        onCheckedChange = { isChecked ->
                            onStateChanged(component.copy(isChecked = isChecked))
                        }
                    )
                    Text(component.label)
                }
            }
            is RadioButtonComponent -> {
                Row(
                    modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        selected = component.isSelected,
                        onClick = {
                            onStateChanged(component.copy(isSelected = !component.isSelected))
                        }
                    )
                    Text(component.label)
                }
            }
            is DropdownComponent -> {
                Column(
                    modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth()
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    val selectedOption = component.selectedOption ?: component.options.firstOrNull() ?: ""

                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedOption)
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Açılır liste"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        component.options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    expanded = false
                                    onStateChanged(component.copy(selectedOption = option))
                                }
                            )
                        }
                    }
                }
            }
            is SwitchComponent -> {
                Row(
                    modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(component.label)
                    Switch(
                        checked = component.isChecked,
                        onCheckedChange = { isChecked ->
                            onStateChanged(component.copy(isChecked = isChecked))
                        }
                    )
                }
            }
        }
    }
} 