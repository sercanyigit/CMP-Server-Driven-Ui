package com.sercan.cmp_server_driven_ui.components.mobil.rendercomponents

import androidx.compose.foundation.layout.Column
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
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun TextFieldComponentRender(
    component: TextFieldComponent,
    onComponentStateChanged: (TextFieldComponent) -> Unit
) {
    Column(
        modifier = component.position.toModifier().then(
            component.style?.toModifier() ?: Modifier
        ).fillMaxWidth()
    ) {
        OutlinedTextField(
            value = component.value ?: "",
            onValueChange = { newValue ->
                onComponentStateChanged(component.copy(value = newValue))
            },
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
}