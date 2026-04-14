package com.mrh.popcorn.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavRoute(
    val label: String,
    val icon: ImageVector,
    val routeObject: Any
)
