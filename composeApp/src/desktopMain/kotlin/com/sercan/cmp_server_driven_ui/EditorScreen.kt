package com.sercan.cmp_server_driven_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sercan.cmp_server_driven_ui.editor.ScreenEditor

@Composable
actual fun EditorScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        ScreenEditor()
    }
} 