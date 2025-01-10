package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent

expect class ScreenService() {
    suspend fun saveScreen(screenId: String, components: List<UiComponent>)
    suspend fun loadScreen(screenId: String): List<UiComponent>
    suspend fun readCommonJson(): String
}