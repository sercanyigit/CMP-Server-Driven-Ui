package com.sercan.cmp_server_driven_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sercan.cmp_server_driven_ui.service.ScreenService
import androidx.compose.runtime.CompositionLocalProvider
import com.sercan.cmp_server_driven_ui.service.LocalScreenService

class MainActivity : ComponentActivity() {
    private val screenService = ScreenService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        screenService.setContext(this)
        println("Context set edildi: ${this != null}")
        
        setContent {
            CompositionLocalProvider(
                LocalScreenService provides screenService
            ) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}