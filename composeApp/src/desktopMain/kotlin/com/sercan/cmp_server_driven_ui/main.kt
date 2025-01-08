package com.sercan.cmp_server_driven_ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sercan.cmp_server_driven_ui.service.LocalScreenService
import com.sercan.cmp_server_driven_ui.service.ScreenService

fun main() = application {
    val screenService = ScreenService()
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMP-Server-Driven-Ui",
    ) {
        CompositionLocalProvider(LocalScreenService provides screenService) {
            App()
        }
    }
}