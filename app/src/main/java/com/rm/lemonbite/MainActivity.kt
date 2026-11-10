package com.rm.lemonbite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.rm.lemonbite.domain.StartState
import com.rm.lemonbite.presentation.navigation.AppNavGraph
import com.rm.lemonbite.ui.theme.LemonBiteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle =
            SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
            navigationBarStyle =
            SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
        )

        setContent {
            LemonBiteTheme {
                val startState = mainViewModel.startState.collectAsStateWithLifecycle().value

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    when (startState) {
                        StartState.Loading -> Unit
                        StartState.Home ->
                            AppNavGraph(
                                Modifier
                                    .background(color = colorResource(R.color.background))
                                    .padding(innerPadding)
                                    .consumeWindowInsets(innerPadding),
                                navController,
                                StartState.Home,
                            )

                        StartState.Welcome ->
                            AppNavGraph(
                                Modifier
                                    .background(color = colorResource(R.color.background))
                                    .padding(innerPadding)
                                    .consumeWindowInsets(innerPadding),
                                navController,
                                StartState.Welcome,
                            )
                    }
                }
            }
        }
    }
}
