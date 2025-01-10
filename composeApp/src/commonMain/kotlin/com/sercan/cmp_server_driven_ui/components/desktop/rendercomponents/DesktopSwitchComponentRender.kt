package com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents

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
import com.sercan.cmp_server_driven_ui.components.mobil.models.SwitchComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil

@Composable
fun DesktopSwitchComponentRender(
    component: SwitchComponent,
    onStateChanged: (SwitchComponent) -> Unit
) {
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