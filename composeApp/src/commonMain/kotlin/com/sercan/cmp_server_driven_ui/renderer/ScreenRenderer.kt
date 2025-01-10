package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import com.sercan.cmp_server_driven_ui.util.getAlignment
import com.sercan.cmp_server_driven_ui.util.toModifier
import com.sercan.cmp_server_driven_ui.util.toTextStyle

@Composable
fun ScreenRenderer(
    components: List<UiComponent>,
    onComponentStateChanged: (UiComponent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        components.forEach { component ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = component.position.getAlignment()
            ) {
                val modifier = component.position.toModifier()
                
                when (component) {
                    is TextComponent -> {
                        Text(
                            text = component.text,
                            modifier = modifier.then(
                                component.style?.toModifier() ?: Modifier
                            )
                        )
                    }
                    is ButtonComponent -> {
                        Button(
                            onClick = { /* onClick işlemi */ },
                            modifier = modifier.then(
                                component.style?.toModifier() ?: Modifier
                            )
                        ) {
                            Text(component.text)
                        }
                    }
                    is TextFieldComponent -> {
                        TextField(
                            value = component.value,
                            onValueChange = { onComponentStateChanged(component.copy(value = it)) },
                            label = { Text(component.label ?: "") },
                            placeholder = { Text(component.hint) },
                            modifier = modifier.then(
                                component.style?.toModifier() ?: Modifier
                            )
                        )
                    }
                    is CheckboxComponent -> {
                        Row(
                            modifier = modifier.then(component.style?.toModifier() ?: Modifier),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = component.isChecked,
                                onCheckedChange = { 
                                    onComponentStateChanged(component.copy(isChecked = it))
                                }
                            )
                            Text(component.label)
                        }
                    }
                    is RadioButtonComponent -> {
                        Row(
                            modifier = modifier.then(component.style?.toModifier() ?: Modifier),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = component.isSelected,
                                onClick = { 
                                    onComponentStateChanged(component.copy(isSelected = !component.isSelected))
                                }
                            )
                            Text(component.label)
                        }
                    }
                    is DropdownComponent -> {
                        var expanded by remember { mutableStateOf(false) }
                        Column(
                            modifier = modifier.then(component.style?.toModifier() ?: Modifier)
                        ) {
                            OutlinedButton(
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(component.selectedOption ?: component.label)
                                Icon(Icons.Default.ArrowDropDown, null)
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
                                            onComponentStateChanged(component.copy(selectedOption = option))
                                        }
                                    )
                                }
                            }
                        }
                    }
                    is SwitchComponent -> {
                        Row(
                            modifier = modifier.then(component.style?.toModifier() ?: Modifier),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(component.label)
                            Switch(
                                checked = component.isChecked,
                                onCheckedChange = { 
                                    onComponentStateChanged(component.copy(isChecked = it))
                                }
                            )
                        }
                    }
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
                var hint by remember(component.id) { mutableStateOf(component.hint) }
                var value by remember(component.id) { mutableStateOf(component.value) }
                TextField(
                    value = value,
                    onValueChange = { newText ->
                        value = newText
                        onStateChanged(component.copy(hint = hint, value = value))
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