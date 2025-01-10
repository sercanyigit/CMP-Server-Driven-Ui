package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
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
        try {
            // Proje kök dizininde resources klasörü oluştur
            val resourcesDir = File("src/commonMain/resources")
            if (!resourcesDir.exists()) {
                resourcesDir.mkdirs()
            }

            // sample_screen.json dosyasını güncelle
            val file = File(resourcesDir, "sample_screen.json")
            val jsonString = json.encodeToString(components)
            file.writeText(jsonString)

            println("Ekran başarıyla kaydedildi: ${file.absolutePath}")
        } catch (e: Exception) {
            println("Ekran kaydedilirken hata oluştu: ${e.message}")
            e.printStackTrace()
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
