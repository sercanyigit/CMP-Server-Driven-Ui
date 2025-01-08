package com.sercan.cmp_server_driven_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.model.UiComponent
import com.sercan.cmp_server_driven_ui.renderer.ScreenRenderer
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import com.sercan.cmp_server_driven_ui.service.ScreenService
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
        scope.launch {
            try {
                println("Ekran yükleme başladı")
                isLoading = true
                val loadedComponents = screenService.loadScreen("current_screen")
                println("Yüklenen bileşenler: ${loadedComponents.size}")
                components = loadedComponents
            } catch (e: Exception) {
                println("Ekran yüklenirken hata: ${e.message}")
                e.printStackTrace()
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Ekran yükleniyor...")
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
                    Text("Bileşen bulunamadı")
                    Text("Lütfen sample_screen.json dosyasını kontrol edin", 
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            else -> {
                ScreenRenderer(components = components)
            }
        }
    }
}