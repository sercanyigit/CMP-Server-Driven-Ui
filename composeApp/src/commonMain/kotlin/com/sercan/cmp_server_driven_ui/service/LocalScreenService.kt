package com.sercan.cmp_server_driven_ui.service

import androidx.compose.runtime.compositionLocalOf

val LocalScreenService = compositionLocalOf<ScreenService> { error("ScreenService not provided") } 