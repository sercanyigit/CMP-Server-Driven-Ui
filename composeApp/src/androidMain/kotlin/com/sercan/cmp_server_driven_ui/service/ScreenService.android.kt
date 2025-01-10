package com.sercan.cmp_server_driven_ui.service

import android.content.Context
import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

actual class ScreenService {
    private var context: Context? = null
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
    
    fun setContext(androidContext: Context) {
        context = androidContext
    }
    
    actual suspend fun saveScreen(screenId: String, components: List<UiComponent>) {
        context?.openFileOutput("$screenId.json", Context.MODE_PRIVATE)?.use {
            it.write(json.encodeToString(components).toByteArray())
        }
    }
    
    actual suspend fun loadScreen(screenId: String): List<UiComponent> {
        return try {
            val jsonText = readCommonJson()
            json.decodeFromString(jsonText)
        } catch (e: Exception) {
            println("Ekran yüklenirken hata: ${e.message}")
            emptyList()
        }
    }

    actual suspend fun readCommonJson(): String {
        return try {
            val classLoader = ScreenService::class.java.classLoader
                ?: throw Exception("ClassLoader bulunamadı")
            
            classLoader.getResourceAsStream("sample_screen.json")?.bufferedReader()?.use { 
                it.readText() 
            } ?: throw Exception("sample_screen.json bulunamadı")
        } catch (e: Exception) {
            println("JSON dosyası okunamadı: ${e.message}")
            e.printStackTrace()
            "[]"
        }
    }
} 