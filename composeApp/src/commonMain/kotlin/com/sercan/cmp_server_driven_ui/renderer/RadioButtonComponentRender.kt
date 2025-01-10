package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun RadioButtonComponentRender(component: RadioButtonComponent, onComponentStateChanged: (RadioButtonComponent) -> Unit){
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