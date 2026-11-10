package com.rm.lemonbite.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoutes {
    @Serializable
    data object WelcomeScreen

    @Serializable
    data object SignUpScreen

    @Serializable
    data object PreferencesScreen

    @Serializable
    data object HomeScreen

    @Serializable
    data class DishDetailsScreen(val itemId: Int)

    @Serializable
    data object ProfileScreen

    @Serializable
    data object OrderSummaryScreen

    @Serializable
    data object TrackOrderScreen
}
