package com.sercan.cmp_server_driven_ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sercan.cmp_server_driven_ui.components.mobil.enums.HorizontalAlignment
import com.sercan.cmp_server_driven_ui.components.mobil.enums.WidthSize
import com.sercan.cmp_server_driven_ui.components.mobil.models.ComponentStyle
import com.sercan.cmp_server_driven_ui.components.mobil.models.Position

@Composable
fun Position.toModifier(): Modifier {
    return Modifier
        .offset(x.dp, y.dp)
        .then(
            when (widthSize) {
                WidthSize.FIXED -> Modifier.width(width.dp)
                WidthSize.HALF -> Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 8.dp)
                WidthSize.FULL -> Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            }
        )
        .height(height.dp)
}

@Composable
fun Position.getAlignment(): Alignment {
    return when (alignment) {
        HorizontalAlignment.START -> Alignment.CenterStart
        HorizontalAlignment.CENTER -> Alignment.Center
        HorizontalAlignment.END -> Alignment.CenterEnd
    }
}

fun ComponentStyle.toModifier(): Modifier {
    var modifier = Modifier
    
    backgroundColor?.let {
        try {
            val color = Color(it.removePrefix("#").toLong(16))
            modifier = modifier.background(color) as Modifier.Companion
        } catch (e: Exception) {
            println("Renk dönüştürme hatası: $it")
        }
    }
    
    padding?.let {
        modifier = modifier.padding(it.dp) as Modifier.Companion
    }

    horizontalPadding?.let {
        modifier = modifier.padding(horizontal = it.dp) as Modifier.Companion
    }
    
    return modifier
}