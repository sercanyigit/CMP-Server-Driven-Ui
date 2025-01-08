package com.sercan.cmp_server_driven_ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMP-Server-Driven-Ui",
    ) {
        App()
    }
}