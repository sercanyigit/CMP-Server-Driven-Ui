package com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil

@Composable
fun DesktopButtonComponentRenderer(component: ButtonComponent){
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