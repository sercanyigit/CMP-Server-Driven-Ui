package com.sercan.cmp_server_driven_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.sercan.cmp_server_driven_ui.editor.ScreenEditor
import kotlinx.coroutines.launch

@Composable
actual fun EditorScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            ScreenEditor(
                onSaveRequest = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Ekran başarıyla kaydedildi")
                    }
                }
            )
        }
    }
} 