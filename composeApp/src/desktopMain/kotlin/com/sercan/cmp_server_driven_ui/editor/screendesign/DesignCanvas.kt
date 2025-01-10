package com.sercan.cmp_server_driven_ui.editor.screendesign

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.Position
import com.sercan.cmp_server_driven_ui.util.ColorUtil

@Composable
fun DesignCanvas(
    components: List<UiComponent>,
    selectedComponent: UiComponent?,
    onComponentSelected: (UiComponent) -> Unit,
    onComponentMoved: (UiComponent, Position) -> Unit,
    onComponentDeleted: (UiComponent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        // Telefon çerçevesi
        Box(
            modifier = Modifier
                .width(360.dp)
                .height(640.dp)
                .background(ColorUtil.Background)
                .border(
                    width = 16.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            // Grid sistemi
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                components.forEach { component ->
                    DraggableComponent(
                        component = component,
                        isSelected = component == selectedComponent,
                        onSelected = { onComponentSelected(component) },
                        onMoved = { newPosition -> onComponentMoved(component, newPosition) },
                        onDelete = { onComponentDeleted(component) }
                    )
                }
            }
        }
    }
}