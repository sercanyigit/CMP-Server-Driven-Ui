package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.ButtonComponent
import com.sercan.cmp_server_driven_ui.model.CheckboxComponent
import com.sercan.cmp_server_driven_ui.model.DropdownComponent
import com.sercan.cmp_server_driven_ui.model.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.model.SwitchComponent
import com.sercan.cmp_server_driven_ui.model.TextComponent
import com.sercan.cmp_server_driven_ui.model.TextFieldComponent
import com.sercan.cmp_server_driven_ui.model.UiComponent
import com.sercan.cmp_server_driven_ui.util.toModifier
import com.sercan.cmp_server_driven_ui.util.toTextStyle

@Composable
fun ScreenRenderer(
    components: List<UiComponent>,
    onComponentStateChanged: (UiComponent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        components.forEach { component ->
            when (component) {
                is TextComponent -> {
                    Text(
                        text = component.text,
                        modifier = component.position.toModifier().then(
                            component.style?.toModifier() ?: Modifier
                        ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                is ButtonComponent -> {
                    Button(
                        onClick = { /* onClick işlemi */ },
                        modifier = component.position.toModifier().then(
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
                        modifier = component.position.toModifier().then(
                            component.style?.toModifier() ?: Modifier
                        ).fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge
                    )
                }
                is CheckboxComponent -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = component.position.toModifier().then(
                            component.style?.toModifier() ?: Modifier
                        ).fillMaxWidth()
                    ) {
                        component.options.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Checkbox(
                                    checked = option in component.selectedOptions,
                                    onCheckedChange = { isChecked -> 
                                        val updatedSelection = if (isChecked) {
                                            component.selectedOptions + option
                                        } else {
                                            component.selectedOptions - option
                                        }
                                        onComponentStateChanged(component.copy(selectedOptions = updatedSelection))
                                    }
                                )
                                Text(
                                    option,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                is RadioButtonComponent -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = component.position.toModifier().then(
                            component.style?.toModifier() ?: Modifier
                        ).fillMaxWidth()
                    ) {
                        component.options.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                RadioButton(
                                    selected = component.selectedOption == option,
                                    onClick = { 
                                        onComponentStateChanged(component.copy(selectedOption = option))
                                    }
                                )
                                Text(
                                    option,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                is DropdownComponent -> {
                    var expanded by remember { mutableStateOf(false) }
                    var selectedItem by remember { mutableStateOf(component.selectedOption) }

                    Box(
                        modifier = component.position.toModifier().then(
                            component.style?.toModifier() ?: Modifier
                        ).fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    selectedItem ?: component.label,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Icon(Icons.Default.ArrowDropDown, null)
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            component.options.forEach { option ->
                                DropdownMenuItem(
                                    text = { 
                                        Text(
                                            option,
                                            style = MaterialTheme.typography.bodyLarge
                                        ) 
                                    },
                                    onClick = {
                                        selectedItem = option
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = component.position.toModifier().then(
                            component.style?.toModifier() ?: Modifier
                        ).fillMaxWidth()
                    ) {
                        Text(
                            component.label,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Switch(
                            checked = component.isChecked,
                            onCheckedChange = { onComponentStateChanged(component.copy(isChecked = it)) }
                        )
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
                    component.options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Checkbox(
                                checked = option in component.selectedOptions,
                                onCheckedChange = { isChecked -> 
                                    val updatedSelection = if (isChecked) {
                                        component.selectedOptions + option
                                    } else {
                                        component.selectedOptions - option
                                    }
                                    onStateChanged(component.copy(selectedOptions = updatedSelection))
                                }
                            )
                            Text(option)
                        }
                    }
                }
            }
            is RadioButtonComponent -> {
                Row(
                    modifier = (component.style?.toModifier() ?: Modifier).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    component.options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RadioButton(
                                selected = component.selectedOption == option,
                                onClick = { 
                                    onStateChanged(component.copy(selectedOption = option))
                                }
                            )
                            Text(option)
                        }
                    }
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