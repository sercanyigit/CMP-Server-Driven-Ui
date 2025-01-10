package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sercan.cmp_server_driven_ui.components.SwitchComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun SwitchComponentRender(component: SwitchComponent, onComponentStateChanged: (SwitchComponent) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = component.position.toModifier().then(
            component.style?.toModifier() ?: Modifier
        ).fillMaxWidth()
    ) {
        Text(
            component.label,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = ColorUtil.TextPrimary
            )
        )
        Switch(
            checked = component.isChecked,
            onCheckedChange = {
                onComponentStateChanged(component.copy(isChecked = it))
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = ColorUtil.Background,
                checkedTrackColor = ColorUtil.Primary,
                uncheckedThumbColor = ColorUtil.Background,
                uncheckedTrackColor = ColorUtil.Disabled
            )
        )
    }
}