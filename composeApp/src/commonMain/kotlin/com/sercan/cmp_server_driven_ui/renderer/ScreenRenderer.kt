package com.sercan.cmp_server_driven_ui.renderer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.*

@Composable
fun ScreenRenderer(components: List<UiComponent>) {
    Box {
        components.forEach { component ->
            ComponentRenderer(component)
        }
    }
}

@Composable
private fun ComponentRenderer(component: UiComponent) {
    Box(
        modifier = Modifier
            .offset(component.position.x.dp, component.position.y.dp)
            .size(component.position.width.dp, component.position.height.dp)
    ) {
        when (component) {
            is TextComponent -> component.style?.toTextStyle()?.let {
                Text(
                    text = component.text,
                    style = it
                )
            }
            is ButtonComponent -> Button(
                onClick = {},
                modifier = component.style?.toModifier() ?: Modifier
            ) {
                Text(component.text)
            }
            is TextFieldComponent -> TextField(
                value = "",
                onValueChange = {},
                label = { Text(component.label ?: "") },
                modifier = component.style?.toModifier() ?: Modifier
            )
        }
    }
} 