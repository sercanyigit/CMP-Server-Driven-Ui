package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.editor.components.DraggableComponent
import com.sercan.cmp_server_driven_ui.model.Position
import com.sercan.cmp_server_driven_ui.model.UiComponent

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
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        // Telefon çerçevesi
        Box(
            modifier = Modifier
                .width(360.dp) // Standart Android telefon genişliği
                .height(640.dp) // ~16:9 aspect ratio
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 16.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            // Grid sistemi
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var currentRow = mutableListOf<UiComponent>()
                components.forEach { component ->
                        // Yeni satıra geç
                        Column (
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            currentRow.forEach { rowComponent ->
                                DraggableComponent(
                                    component = rowComponent,
                                    isSelected = rowComponent == selectedComponent,
                                    onSelected = { onComponentSelected(rowComponent) },
                                    onMoved = { newPosition -> onComponentMoved(rowComponent, newPosition) },
                                    onDelete = { onComponentDeleted(rowComponent) }
                                )
                            }
                        }
                        currentRow = mutableListOf(component)
                }
                // Son satırı ekle
                if (currentRow.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        currentRow.forEach { rowComponent ->
                            DraggableComponent(
                                component = rowComponent,
                                isSelected = rowComponent == selectedComponent,
                                onSelected = { onComponentSelected(rowComponent) },
                                onMoved = { newPosition -> onComponentMoved(rowComponent, newPosition) },
                                onDelete = { onComponentDeleted(rowComponent) }
                            )
                        }
                    }
                }
            }
        }
    }
}