package com.sercan.cmp_server_driven_ui.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun ComponentStyle.toModifier(): Modifier {
    var modifier = Modifier
    
    backgroundColor?.let {
        try {
            val color = Color(it.removePrefix("#").toLong(16) or 0x00000000FF000000)
            modifier = modifier.background(color) as Modifier.Companion
        } catch (e: Exception) {
            println("Renk dönüştürme hatası: $it")
        }
    }
    
    padding?.let {
        modifier = modifier.padding(it.dp) as Modifier.Companion
    }
    
    return modifier
}

@Composable
fun ComponentStyle.toTextStyle(): TextStyle {
    return MaterialTheme.typography.bodyMedium.copy(
        color = textColor?.let {
            try {
                Color(it.removePrefix("#").toLong(16) or 0x00000000FF000000)
            } catch (e: Exception) {
                println("Renk dönüştürme hatası: $it")
                Color.Unspecified
            }
        } ?: Color.Unspecified,
        fontSize = fontSize?.sp ?: MaterialTheme.typography.bodyMedium.fontSize
    )
} 