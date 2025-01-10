package com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil


@Composable
fun DesktopTextComponentRenderer(component: TextComponent){
    OutlinedTextField(
        value = component.text,
        onValueChange = { },
        readOnly = true,
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ColorUtil.Primary,
            unfocusedBorderColor = ColorUtil.Border,
            disabledBorderColor = ColorUtil.Border,
            disabledTextColor = ColorUtil.TextPrimary
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = ColorUtil.TextPrimary
        )
    )
}