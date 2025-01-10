package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.ButtonComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun ButtonComponentRender(component: ButtonComponent) {
    Button(
        onClick = { /* onClick işlemi */ },
        modifier = component.position.toModifier().then(
            component.style?.toModifier() ?: Modifier
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorUtil.Primary,
            contentColor = ColorUtil.Background,
            disabledContainerColor = ColorUtil.Disabled,
            disabledContentColor = ColorUtil.DisabledText
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            component.text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}