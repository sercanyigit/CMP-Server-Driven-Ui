package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sercan.cmp_server_driven_ui.components.TextComponent
import com.sercan.cmp_server_driven_ui.util.ColorUtil
import com.sercan.cmp_server_driven_ui.util.toModifier

@Composable
fun TextComponentRender(component: TextComponent) {
    Text(
        text = component.text,
        modifier = component.position.toModifier().then(
            component.style?.toModifier() ?: Modifier
        ),
        style = MaterialTheme.typography.bodyLarge.copy(
            color = ColorUtil.TextPrimary
        )
    )
}