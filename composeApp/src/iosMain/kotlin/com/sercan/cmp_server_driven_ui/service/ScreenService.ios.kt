package com.sercan.cmp_server_driven_ui.service

import com.sercan.cmp_server_driven_ui.model.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.decodeFromString
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
            val documentsPath = NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true
            ).firstOrNull() as? String ?: return emptyList()
            
            val filePath = "$documentsPath/$screenId.json"
            val fileManager = NSFileManager.defaultManager
            
            if (!fileManager.fileExistsAtPath(filePath)) {
                return emptyList()
            }
            
            val jsonString = NSString.stringWithContentsOfFile(
                filePath,
                encoding = NSUTF8StringEncoding,
                error = null
            ) ?: return emptyList()
            
            json.decodeFromString(jsonString as String)
        } catch (e: Exception) {
            println("Ekran yüklenirken hata: ${e.message}")
            emptyList()
        }
    }
} 