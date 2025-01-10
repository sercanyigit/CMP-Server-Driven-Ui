package com.sercan.cmp_server_driven_ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import com.sercan.cmp_server_driven_ui.service.ScreenService

fun main() = application {
    val screenService = ScreenService()
    val windowState = rememberWindowState(placement = WindowPlacement.Maximized)
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMP-Server-Driven-Ui",
        state = windowState
    ) {
        CompositionLocalProvider(LocalScreenService provides screenService) {
            App()
        }
    }
}