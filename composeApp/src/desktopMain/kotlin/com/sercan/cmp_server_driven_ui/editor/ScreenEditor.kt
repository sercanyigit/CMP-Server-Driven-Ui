package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.*
import com.sercan.cmp_server_driven_ui.service.ScreenService
import kotlinx.coroutines.launch

@Composable
fun ScreenEditor() {
    var components by remember { mutableStateOf(listOf<UiComponent>()) }
    var selectedComponent by remember { mutableStateOf<UiComponent?>(null) }
    val screenService = remember { ScreenService() }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        try {
            components = screenService.loadScreen("current_screen")
        } catch (e: Exception) {
            println("Ekran yüklenirken hata: ${e.message}")
        }
    }
    
    Column {
        // Üst toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { 
                scope.launch {
                    try {
                        components = screenService.loadScreen("current_screen")
                    } catch (e: Exception) {
                        println("Örnek ekran yüklenirken hata: ${e.message}")
                    }
                }
            }) {
                Text("Örnek Ekranı Yükle")
            }
            
            Button(
                onClick = { 
                    scope.launch {
                        screenService.saveScreen("current_screen", components)
                    }
                }
            ) {
                Text("Ekranı Kaydet")
            }
        }
        
        // Ana içerik
        Row {
            ComponentPalette(
                onComponentSelected = { type ->
                    val newComponent = createComponent(type, components.size)
                    components = components + newComponent
                }
            )
            
            DesignCanvas(
                components = components,
                selectedComponent = selectedComponent,
                onComponentSelected = { selectedComponent = it },
                onComponentMoved = { component, newPosition ->
                    components = components.map {
                        if (it.id == component.id) {
                            when (it) {
                                is TextComponent -> it.copy(position = newPosition)
                                is ButtonComponent -> it.copy(position = newPosition)
                                is TextFieldComponent -> it.copy(position = newPosition)
                                else -> it
                            }
                        } else it
                    }
                }
            )
            
            PropertiesPanel(
                selectedComponent = selectedComponent,
                onComponentUpdated = { updated ->
                    components = components.map { if (it.id == updated.id) updated else it }
                }
            )
        }
    }
}

@Composable
private fun ComponentPalette(onComponentSelected: (ComponentType) -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        ComponentType.values().forEach { type ->
            Button(
                onClick = { onComponentSelected(type) },
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                Text("Add ${type.name}")
            }
        }
    }
}

@Composable
private fun DesignCanvas(
    components: List<UiComponent>,
    selectedComponent: UiComponent?,
    onComponentSelected: (UiComponent) -> Unit,
    onComponentMoved: (UiComponent, Position) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .border(1.dp, Color.Gray)
    ) {
        components.forEach { component ->
            DraggableComponent(
                component = component,
                isSelected = component == selectedComponent,
                onSelected = { onComponentSelected(component) },
                onMoved = { newPosition -> onComponentMoved(component, newPosition) }
            )
        }
    }
}

@Composable
private fun DraggableComponent(
    component: UiComponent,
    isSelected: Boolean,
    onSelected: () -> Unit,
    onMoved: (Position) -> Unit
) {
    var position by remember { mutableStateOf(component.position) }

    Box(
        modifier = Modifier
            .offset(position.x.dp, position.y.dp)
            .size(position.width.dp, position.height.dp)
            .border(if (isSelected) 2.dp else 1.dp, if (isSelected) Color.Blue else Color.Gray)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    position = position.copy(
                        x = (position.x + dragAmount.x).toInt(),
                        y = (position.y + dragAmount.y).toInt()
                    )
                    onMoved(position)
                }
            }
            .clickable { onSelected() }
    ) {
        when (component) {
            is TextComponent -> Text(component.text)
            is ButtonComponent -> Button({}) { Text(component.text) }
            is TextFieldComponent -> TextField(
                value = "",
                onValueChange = {},
                label = { Text(component.label ?: "") }
            )
        }
    }
} 