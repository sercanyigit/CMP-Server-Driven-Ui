package com.sercan.cmp_server_driven_ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import com.sercan.cmp_server_driven_ui.editor.ComponentFactory.createComponent
import com.sercan.cmp_server_driven_ui.editor.propertiespanel.PropertiesPanelComponent
import com.sercan.cmp_server_driven_ui.editor.screendesign.DesignCanvas
import com.sercan.cmp_server_driven_ui.editor.searchcomponents.SearchComponentsPanel
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import com.sercan.cmp_server_driven_ui.util.Constant
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun EditorScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

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
                    components = screenService.loadScreen(Constant.homeScreen)
                } catch (e: Exception) {
                    println(e)
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize()) {
                    // Sol panel - Bileşen listesi
                    SearchComponentsPanel(
                        onComponentSelected = { type ->
                            val newComponent = createComponent(type, componentCounter++)
                            components = components + newComponent
                            selectedComponent = newComponent
                        }
                    )

                    // Orta panel - Tasarım alanı
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        DesignCanvas(
                            components = components,
                            selectedComponent = selectedComponent,
                            onComponentSelected = { selectedComponent = it },
                            onComponentMoved = { component, newPosition ->
                                components = components.map { current ->
                                    if (current.id == component.id) current.updatePosition(newPosition)
                                    else current
                                }
                            },
                            onComponentDeleted = { component ->
                                components = components.filter { it.id != component.id }
                                if (selectedComponent?.id == component.id) {
                                    selectedComponent = null
                                }
                            }
                        )
                    }

                    // Sağ panel - Özellikler
                    PropertiesPanelComponent(
                        selectedComponent = selectedComponent,
                        onComponentUpdated = { updatedComponent ->
                            components = components.map { current ->
                                if (current.id == updatedComponent.id) updatedComponent
                                else current
                            }
                            selectedComponent = updatedComponent
                        },
                        onClearRequest = {
                            showClearConfirmation = true
                        },
                        onSaveRequest = {
                            scope.launch {
                                try {
                                    screenService.saveScreen(Constant.homeScreen, components)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Ekran başarıyla kaydedildi")
                                    }
                                } catch (e: Exception) {
                                    // Hata durumunda işlem
                                }
                            }
                        }
                    )
                }
            }

            // Temizleme onayı için BottomSheet
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
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(bottom = 16.dp),
                            tint = MaterialTheme.colorScheme.error
                        )

                        Text(
                            "Tüm componentleri silmek istediğinizden emin misiniz?",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showClearConfirmation = false },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("İptal")
                            }

                            Button(
                                onClick = {
                                    components = emptyList()
                                    selectedComponent = null
                                    showClearConfirmation = false
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Temizle")
                            }
                        }
                    }
                }
            }
        }
    }
} 