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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.DropdownComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun DropdownComponentRender(
    component: DropdownComponent,
    onComponentStateChanged: (DropdownComponent) -> Unit
) {
    Column(
        modifier = component.position.toModifier().then(
            component.style?.toModifier() ?: Modifier
        ).fillMaxWidth()
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
                                        onComponentStateChanged(component.copy(selectedOption = option))
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
                            onComponentStateChanged(component.copy(selectedOption = option))
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