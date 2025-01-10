package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.CheckboxComponent
import com.sercan.cmp_server_driven_ui.components.DropdownComponent
import com.sercan.cmp_server_driven_ui.components.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.components.SwitchComponent
import com.sercan.cmp_server_driven_ui.components.UiComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil

@Composable
fun DesktopPanelComponentRenderer(
    component: UiComponent,
    onStateChanged: (UiComponent) -> Unit
) {
    when (component) {
        is ButtonComponent -> {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorUtil.Primary,
                    contentColor = ColorUtil.Background
                )
            ) {
                Text(
                    component.text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        is CheckboxComponent -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                            onCheckedChange = { checked ->
                                val updatedSelection = if (checked) {
                                    component.selectedOptions + option
                                } else {
                                    component.selectedOptions - option
                                }
                                onStateChanged(component.copy(selectedOptions = updatedSelection))
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = ColorUtil.Primary,
                                uncheckedColor = ColorUtil.TextSecondary
                            )
                        )
                        Text(
                            option,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ColorUtil.TextPrimary
                            )
                        )
                    }
                }
            }
        }
        is SwitchComponent -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    component.label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = ColorUtil.TextPrimary
                    )
                )
                Switch(
                    checked = component.isChecked,
                    onCheckedChange = { checked ->
                        onStateChanged(component.copy(isChecked = checked))
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = ColorUtil.Background,
                        checkedTrackColor = ColorUtil.Primary,
                        uncheckedThumbColor = ColorUtil.Background,
                        uncheckedTrackColor = ColorUtil.Border
                    )
                )
            }
        }
        is RadioButtonComponent -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
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
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ColorUtil.Primary,
                                unselectedColor = ColorUtil.TextSecondary
                            )
                        )
                        Text(
                            option,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ColorUtil.TextPrimary
                            )
                        )
                    }
                }
            }
        }
        is DropdownComponent -> {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    var selectedItem by remember { mutableStateOf(component.selectedOption) }

                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = ColorUtil.Background,
                            contentColor = ColorUtil.TextPrimary
                        ),
                        border = BorderStroke(1.dp, ColorUtil.Border)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                selectedItem ?: component.label,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = if (selectedItem != null) ColorUtil.TextPrimary else ColorUtil.TextSecondary
                                )
                            )
                            Icon(
                                Icons.Default.ArrowDropDown,
                                null,
                                tint = ColorUtil.TextSecondary
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ColorUtil.Background)
                    ) {
                        component.options.forEach { option ->
                            val isSelected = option == selectedItem
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = {
                                                selectedItem = option
                                                expanded = false
                                                onStateChanged(component.copy(selectedOption = option))
                                            },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = ColorUtil.Primary,
                                                unselectedColor = ColorUtil.TextSecondary
                                            )
                                        )
                                        Text(
                                            option,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = if (isSelected) ColorUtil.Primary else ColorUtil.TextPrimary
                                            )
                                        )
                                    }
                                },
                                onClick = {
                                    selectedItem = option
                                    expanded = false
                                    onStateChanged(component.copy(selectedOption = option))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isSelected) ColorUtil.SelectedBackground else ColorUtil.Background
                                    )
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }
        else -> {}
    }
}