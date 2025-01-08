package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.model.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File

actual class ScreenService {
    private val json = Json { 
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
    
    actual suspend fun saveScreen(screenId: String, components: List<UiComponent>) {
        val file = File("screens/$screenId.json")
        file.parentFile.mkdirs()
        file.writeText(json.encodeToString(components))
    }
    
    actual suspend fun loadScreen(screenId: String): List<UiComponent> {
        val file = File("screens/$screenId.json")
        if (!file.exists()) return emptyList()
        return json.decodeFromString(file.readText())
    }
    
    fun loadSampleScreen(): List<UiComponent> {
        return try {
            val inputStream = javaClass.getResourceAsStream("/sample_screen.json")
            val jsonString = inputStream?.bufferedReader()?.use { it.readText() }
            jsonString?.let { json.decodeFromString<List<UiComponent>>(it) } ?: emptyList()
        } catch (e: Exception) {
            println("Örnek ekran yüklenirken hata: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
} 