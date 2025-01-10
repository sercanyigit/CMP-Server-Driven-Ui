package com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.CheckboxComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun CheckboxComponentRender(
    component: CheckboxComponent,
    onComponentStateChanged: (UiComponent) -> Unit
) {
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
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = ColorUtil.Primary,
                        uncheckedColor = ColorUtil.TextSecondary,
                        checkmarkColor = ColorUtil.Background
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