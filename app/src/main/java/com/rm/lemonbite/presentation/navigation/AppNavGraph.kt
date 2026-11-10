package com.rm.lemonbite.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rm.lemonbite.domain.StartState
import com.rm.lemonbite.presentation.screens.ProfileScreen
import com.rm.lemonbite.presentation.screens.WelcomeScreen
import com.rm.lemonbite.presentation.screens.dishDetailScreen.DishDetailsScreen
import com.rm.lemonbite.presentation.screens.home.HomeScreen
import com.rm.lemonbite.presentation.screens.orderSummary.OrderSummaryScreen
import com.rm.lemonbite.presentation.screens.preferences.PreferencesScreen
import com.rm.lemonbite.presentation.screens.signup.SignUpScreen
import com.rm.lemonbite.presentation.screens.trackOrder.TrackOrderScreen

@Composable
fun AppNavGraph(modifier: Modifier, navHostController: NavHostController, startState: StartState) {
    NavHost(
        navController = navHostController,
        startDestination =
        if (startState == StartState.Welcome) {
            NavRoutes.WelcomeScreen
        } else {
            NavRoutes.HomeScreen
        },
    ) {
        composable<NavRoutes.WelcomeScreen> {
            WelcomeScreen(
                modifier,
                onGetStartedClicked = {
                    navHostController.navigate(NavRoutes.SignUpScreen)
                },
            )
        }

        composable<NavRoutes.SignUpScreen> {
            SignUpScreen(
                modifier,
                onNextClicked = {
                    navHostController.navigate(
                        NavRoutes.PreferencesScreen,
                    )
                },
            )
        }

        composable<NavRoutes.PreferencesScreen> {
            PreferencesScreen(
                modifier = modifier,
                onNextClicked = {
                    navHostController.navigate(NavRoutes.HomeScreen) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable<NavRoutes.HomeScreen> {
            HomeScreen(
                modifier = modifier,
                navigateToCartScreen = {
                    navHostController.navigate(NavRoutes.OrderSummaryScreen)
                },
                onItemSelected = {
                    navHostController.navigate(NavRoutes.DishDetailsScreen(it))
                },
                navigateToProfileScreen = {
                    navHostController.navigate(NavRoutes.ProfileScreen)
                },
            )
        }

        composable<NavRoutes.DishDetailsScreen> {
            DishDetailsScreen(
                modifier = modifier,
                navigateToCartScreen = {
                    navHostController.navigate(NavRoutes.OrderSummaryScreen)
                },
                navigateToProfileScreen = {
                    navHostController.navigate(NavRoutes.ProfileScreen)
                },
                onAddToCartClicked = {
                    navHostController.navigate(NavRoutes.OrderSummaryScreen)
                },
                onBackClick = {
                    navHostController.popBackStack()
                },
            )
        }

        composable<NavRoutes.ProfileScreen> {
            ProfileScreen(
                modifier = modifier,
                navigateToCartScreen = {
                    navHostController.navigate(NavRoutes.OrderSummaryScreen)
                },
                onBackClick = {
                    navHostController.popBackStack()
                },
            )
        }

        composable<NavRoutes.OrderSummaryScreen> {
            OrderSummaryScreen(
                modifier = modifier,
                navigateToTrackOrder = {
                    navHostController.navigate(NavRoutes.TrackOrderScreen)
                },
                navigateToProfileScreen = {
                    navHostController.navigate(NavRoutes.ProfileScreen)
                },
                onBackClick = {
                    navHostController.navigate(NavRoutes.HomeScreen) {
                        popUpTo(NavRoutes.HomeScreen) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable<NavRoutes.TrackOrderScreen> {
            TrackOrderScreen(
                modifier = modifier,
                navigateToCartScreen = {
                    navHostController.navigate(NavRoutes.OrderSummaryScreen)
                },
                onBackClicked = {
                    navHostController.navigate(NavRoutes.HomeScreen) {
                        popUpTo(NavRoutes.HomeScreen) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                navigateToProfileScreen = {
                    navHostController.navigate(NavRoutes.ProfileScreen)
                },
            )
        }
    }
}
