package com.sercan.cmp_server_driven_ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.*
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import com.sercan.cmp_server_driven_ui.renderer.ComponentRenderer
import kotlinx.coroutines.launch

// ComponentPanel ve ComponentFactory'yi aynı pakette olduğu için direkt import ediyoruz
import com.sercan.cmp_server_driven_ui.editor.ComponentFactory.createComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenEditor(
    onSaveRequest: () -> Unit = {}
) {
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
                    },
                    onComponentDeleted = { componentToDelete ->
                        components = components.filter { it.id != componentToDelete.id }
                        if (selectedComponent?.id == componentToDelete.id) {
                            selectedComponent = null
                        }
                    }
                )
            }

            // Sağ panel - Özellikler
            PropertiesPanel(
                selectedComponent = selectedComponent,
                onComponentUpdated = { updated ->
                    components = components.map { if (it.id == updated.id) updated else it }
                    selectedComponent = updated
                },
                onClearRequest = { showClearConfirmation = true },
                onSaveRequest = {
                    scope.launch {
                        try {
                            screenService.saveScreen("current_screen", components)
                            onSaveRequest()
                        } catch (e: Exception) {
                            // Hata mesajı gösterilebilir
                        }
                    }
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
                // Componentleri grid hücrelerine yerleştir
                var currentRow = mutableListOf<UiComponent>()
                components.forEach { component ->
                    if (currentRow.sumOf { it.position.width } + component.position.width > 360) {
                        // Yeni satıra geç
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
                        currentRow = mutableListOf(component)
                    } else {
                        currentRow.add(component)
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DraggableComponent(
    component: UiComponent,
    isSelected: Boolean,
    onSelected: () -> Unit,
    onMoved: (Position) -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier
            .width(component.position.width.dp)
            .height(component.position.height.dp)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline
            )
            .clickable { onSelected() }
    ) {
        ComponentRenderer(
            component,
            onStateChanged = {}
        )

        // Silme ikonu - sadece seçili bileşen için göster
        if (isSelected) {
            IconButton(
                onClick = { showDeleteConfirmation = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Silme onayı için BottomSheet
    if (showDeleteConfirmation) {
        ModalBottomSheet(
            onDismissRequest = { showDeleteConfirmation = false },
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
                    "Bu bileşeni silmek istediğinizden emin misiniz?",
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
                        onClick = { showDeleteConfirmation = false }
                    ) {
                        Text("İptal")
                    }

                    Button(
                        onClick = {
                            onDelete()
                            showDeleteConfirmation = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Evet, Sil")
                    }
                }
            }
        }
    }
}
