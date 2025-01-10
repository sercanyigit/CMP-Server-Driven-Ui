package com.sercan.cmp_server_driven_ui.components.desktop.rendercomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil

@Composable
fun DesktopTextFieldComponentRender(component: TextFieldComponent){
    OutlinedTextField(
        value = component.value ?: "",
        onValueChange = { },
        readOnly = true,
        enabled = false,
        label = {
            Text(
                component.label ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ColorUtil.TextSecondary
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ColorUtil.Primary,
            unfocusedBorderColor = ColorUtil.Border,
            focusedLabelColor = ColorUtil.Primary,
            unfocusedLabelColor = ColorUtil.TextSecondary,
            cursorColor = ColorUtil.Primary
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = ColorUtil.TextPrimary
        )
    )
}