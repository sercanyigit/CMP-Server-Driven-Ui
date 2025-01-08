package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.*
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import com.sercan.cmp_server_driven_ui.renderer.ComponentRenderer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenEditor() {
    val screenService = LocalScreenService.current
    val scope = rememberCoroutineScope()
    
    var components by remember { mutableStateOf<List<UiComponent>>(emptyList()) }
    var selectedComponent by remember { mutableStateOf<UiComponent?>(null) }
    var componentCounter by remember { mutableStateOf(0) }
    var showClearConfirmation by remember { mutableStateOf(false) }

    // BottomSheet için state
    val bottomSheetState = rememberModalBottomSheetState()
    
    LaunchedEffect(Unit) {
        try {
            components = screenService.loadScreen("current_screen")
        } catch (e: Exception) {
            // Hata durumunda işlem
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sol panel - Bileşen listesi
            ComponentPanel(
                onComponentSelected = { type ->
                    val newComponent = createComponent(type, componentCounter++)
                    components = components + newComponent
                    selectedComponent = newComponent
                }
            )

            // Orta panel - Tasarım alanı
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
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
                                    is CheckboxComponent -> it.copy(position = newPosition)
                                    is RadioButtonComponent -> it.copy(position = newPosition)
                                    is DropdownComponent -> it.copy(position = newPosition)
                                    is SwitchComponent -> it.copy(position = newPosition)
                                }
                            } else it
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    // Temizle butonu
                    Button(
                        onClick = { showClearConfirmation = true },
                        modifier = Modifier.padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Temizle",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Temizle")
                    }

                    // Kaydet butonu
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    screenService.saveScreen("current_screen", components)
                                } catch (e: Exception) {
                                    // Hata mesajı gösterilebilir
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Kaydet",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Kaydet")
                    }
                }
            }

            // Sağ panel - Özellikler
            PropertiesPanel(
                selectedComponent = selectedComponent,
                onComponentUpdated = { updated ->
                    components = components.map { if (it.id == updated.id) updated else it }
                    selectedComponent = updated
                }
            )
        }

        // Onay BottomSheet'i
        if (showClearConfirmation) {
            ModalBottomSheet(
                onDismissRequest = { showClearConfirmation = false },
                sheetState = bottomSheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Uyarı",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        "Tüm bileşenleri silmek istediğinizden emin misiniz?",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        "Bu işlem geri alınamaz.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { showClearConfirmation = false }
                        ) {
                            Text("İptal")
                        }
                        
                        Button(
                            onClick = {
                                components = emptyList()
                                selectedComponent = null
                                showClearConfirmation = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Evet, Temizle")
                        }
                    }
                }
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
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline)
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
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary 
                       else MaterialTheme.colorScheme.outline
            )
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
        ComponentRenderer(component)
    }
}