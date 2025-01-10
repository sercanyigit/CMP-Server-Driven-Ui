package com.sercan.cmp_server_driven_ui.editor.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.SwitchRight
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.enums.ComponentType


@Composable
fun ComponentItem(
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
                    ComponentType.TEXT -> Icons.Default.Label
                    ComponentType.BUTTON -> Icons.Default.SmartButton
                    ComponentType.TEXT_FIELD -> Icons.Default.TextFields
                    ComponentType.CHECKBOX -> Icons.Default.CheckBox
                    ComponentType.RADIO_BUTTON -> Icons.Default.RadioButtonChecked
                    ComponentType.DROPDOWN -> Icons.Default.ArrowDropDown
                    ComponentType.SWITCH -> Icons.Default.SwitchRight
                },
                contentDescription = componentType.name,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = when (componentType) {
                    ComponentType.TEXT -> "Label"
                    ComponentType.BUTTON -> "Buton"
                    ComponentType.TEXT_FIELD -> "Text Field"
                    ComponentType.CHECKBOX -> "Checkbox"
                    ComponentType.RADIO_BUTTON -> "Radio buton"
                    ComponentType.DROPDOWN -> "Liste"
                    ComponentType.SWITCH -> "Switch"
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}