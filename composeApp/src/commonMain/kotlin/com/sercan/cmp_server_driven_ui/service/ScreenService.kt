package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.model.UiComponent

expect class ScreenService() {
    suspend fun saveScreen(screenId: String, components: List<UiComponent>)
    suspend fun loadScreen(screenId: String): List<UiComponent>
} 