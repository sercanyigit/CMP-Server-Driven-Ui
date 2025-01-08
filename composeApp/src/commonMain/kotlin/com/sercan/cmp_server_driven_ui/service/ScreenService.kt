package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.model.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

expect class ScreenService() {
    suspend fun saveScreen(screenId: String, components: List<UiComponent>)
    suspend fun loadScreen(screenId: String): List<UiComponent>
    suspend fun readCommonJson(): String
}

// Ortak JSON yapılandırması
val commonJson = Json { 
    prettyPrint = true
    ignoreUnknownKeys = true
    isLenient = true
    serializersModule = SerializersModule {
        polymorphic(UiComponent::class) {
            subclass(TextComponent::class)
            subclass(ButtonComponent::class)
            subclass(TextFieldComponent::class)
        }
    }
} 