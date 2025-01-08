package com.sercan.cmp_server_driven_ui.service

import android.content.Context
import com.sercan.cmp_server_driven_ui.model.*
import kotlinx.serialization.decodeFromString
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
        println("LoadScreen çağrıldı: $screenId")
        
        if (context == null) {
            println("HATA: Context null!")
            return emptyList()
        }

        return try {
            // Assets'ten direkt okumayı deneyelim
            context?.assets?.open("sample_screen.json")?.let { inputStream ->
                val jsonText = inputStream.bufferedReader().use { it.readText() }
                println("Assets'ten okunan JSON: $jsonText")
                
                try {
                    val components = json.decodeFromString<List<UiComponent>>(jsonText)
                    println("Başarıyla parse edilen bileşenler: $components")
                    components
                } catch (e: Exception) {
                    println("JSON parse hatası: ${e.message}")
                    e.printStackTrace()
                    emptyList()
                }
            } ?: run {
                println("Assets dosyası açılamadı")
                emptyList()
            }
        } catch (e: Exception) {
            println("Genel hata: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }
} 