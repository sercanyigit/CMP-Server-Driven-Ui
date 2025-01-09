package com.sercan.cmp_server_driven_ui.editor

// ComponentPanel ve ComponentFactory'yi aynı pakette olduğu için direkt import ediyoruz
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
import com.sercan.cmp_server_driven_ui.editor.ComponentFactory.createComponent
import com.sercan.cmp_server_driven_ui.editor.components.PropertiesPanel
import com.sercan.cmp_server_driven_ui.editor.components.SearchLeftPanel
import com.sercan.cmp_server_driven_ui.model.ButtonComponent
import com.sercan.cmp_server_driven_ui.model.CheckboxComponent
import com.sercan.cmp_server_driven_ui.model.DropdownComponent
import com.sercan.cmp_server_driven_ui.model.RadioButtonComponent
import com.sercan.cmp_server_driven_ui.model.SwitchComponent
import com.sercan.cmp_server_driven_ui.model.TextComponent
import com.sercan.cmp_server_driven_ui.model.TextFieldComponent
import com.sercan.cmp_server_driven_ui.model.UiComponent
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import kotlinx.coroutines.launch

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
            SearchLeftPanel(
                onComponentSelected = { type ->
                    val newComponent = createComponent(type, componentCounter++)
                    components = components + newComponent
                    selectedComponent = newComponent
                }
            )

            // Orta panel - Tasarım alanı
            Column (
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

