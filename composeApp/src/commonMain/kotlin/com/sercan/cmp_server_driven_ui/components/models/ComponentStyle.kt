package com.sercan.cmp_server_driven_ui.components.models

import kotlinx.serialization.Serializable

@Serializable
data class ComponentStyle(
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val fontSize: Int? = null,
    val padding: Int? = null,
    val cornerRadius: Int? = null,
    val horizontalPadding: Int? = null
)