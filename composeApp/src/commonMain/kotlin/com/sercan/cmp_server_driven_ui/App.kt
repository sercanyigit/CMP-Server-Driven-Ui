package com.sercan.cmp_server_driven_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sercan.cmp_server_driven_ui.components.UiComponent
import com.sercan.cmp_server_driven_ui.renderer.MobileScreenRenderer
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import kotlinx.coroutines.launch

expect fun getPlatformName(): String

@Composable
expect fun EditorScreen()

@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            when (getPlatformName()) {
                "Android", "iOS" -> MobileApp()
                else -> EditorScreen()
            }
        }
    }
}

@Composable
fun MobileApp() {
    val scope = rememberCoroutineScope()
    val screenService = LocalScreenService.current
    var components by remember { mutableStateOf(emptyList<UiComponent>()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(Unit) {
        try {
            val loadedComponents = screenService.loadScreen("current_screen")
            println("Yüklenen componentler: ${loadedComponents.size}")
            components = loadedComponents
        } catch (e: Exception) {
            println("Ekran yüklenirken hata: ${e.message}")
            e.printStackTrace()
            error = e.message
        } finally {
            isLoading = false
        }
    }
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Hata: $error", color = Color.Red)
                    Button(onClick = {
                        error = null
                        isLoading = true
                        scope.launch {
                            try {
                                components = screenService.loadScreen("current_screen")
                            } catch (e: Exception) {
                                error = e.message
                            } finally {
                                isLoading = false
                            }
                        }
                    }) {
                        Text("Tekrar Dene")
                    }
                }
            }
            components.isEmpty() -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Component bulunamadı")
                    Text("Lütfen sample_screen.json dosyasını kontrol edin", 
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            else -> {
                MobileScreenRenderer(
                    components = components,
                    onComponentStateChanged = { updatedComponent ->
                        components = components.map { 
                            if (it.id == updatedComponent.id) updatedComponent else it 
                        }
                        scope.launch {
                            try {
                                screenService.saveScreen("current_screen", components)
                            } catch (e: Exception) {
                                error = e.message
                            }
                        }
                    }
                )
            }
        }
    }
}