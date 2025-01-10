package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.components.mobil.models.ButtonComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.TextFieldComponent
import com.sercan.cmp_server_driven_ui.components.mobil.models.UiComponent
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import platform.Foundation.*

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
    
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun saveScreen(screenId: String, components: List<UiComponent>) {
        val fileManager = NSFileManager.defaultManager
        val documentsPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).firstOrNull() as? String ?: return
        
        val filePath = "$documentsPath/$screenId.json"
        val jsonString = json.encodeToString(components)
        
        try {
            (jsonString as NSString).writeToFile(
                filePath,
                atomically = true,
                encoding = NSUTF8StringEncoding,
                error = null
            )
        } catch (e: Exception) {
            println("Dosya kaydedilirken hata: ${e.message}")
        }
    }
    
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun loadScreen(screenId: String): List<UiComponent> {
        return try {
            val jsonText = readCommonJson()
            json.decodeFromString(jsonText)
        } catch (e: Exception) {
            println("Ekran yüklenirken hata: ${e.message}")
            emptyList()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun readCommonJson(): String {
        return try {
            val bundle = NSBundle.mainBundle
            val path = bundle.pathForResource("sample_screen", ofType = "json")
                ?: throw Exception("sample_screen.json bulunamadı")
            
            NSString.stringWithContentsOfFile(path, encoding = NSUTF8StringEncoding, error = null)
                ?.toString() ?: throw Exception("Dosya okunamadı")
        } catch (e: Exception) {
            println("JSON dosyası okunamadı: ${e.message}")
            e.printStackTrace()
            "[]"
        }
    }
} 