package com.mrh.popcorn.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.reflect.KClass

data class NavRoute(
    val label: String,
    val icon: ImageVector,
    val routeClass: KClass<*>,
    val navigate: () -> Unit
)
