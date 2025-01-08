package com.sercan.cmp_server_driven_ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform