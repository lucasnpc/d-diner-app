package com.example.ddinerapp.featureMain.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.common.util.AuthenticationState
import com.example.ddinerapp.featureAuthentication.AuthenticationActivity
import com.example.ddinerapp.featureMain.presentation.home.HomeScreen
import com.example.ddinerapp.featureMain.presentation.home.HomeViewModel
import com.example.ddinerapp.featureMain.presentation.orderingDelivery.OrderingDeliveryScreen
import com.example.ddinerapp.featureMain.presentation.orderingDesks.OrderingDesksScreen
import com.example.ddinerapp.featureMain.presentation.orderingType.OrderingTypeScreen
import com.example.ddinerapp.featureMain.presentation.utils.AuthViewModel
import com.example.ddinerapp.featureMain.presentation.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeAuthState()
        installSplashScreen()
        setContent {
            DDinerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.OrderingTypeScreen.route
                    ) {
                        composable(route = Screen.OrderingTypeScreen.route) {
                            OrderingTypeScreen(navController = navController)
                        }
                        composable(route = Screen.OrderingDesksScreen.route) {
                            OrderingDesksScreen(navController = navController)
                        }
                        composable(route = Screen.OrderingDeliveryScreen.route) {
                            OrderingDeliveryScreen(navController = navController)
                        }
                        composable(
                            route = Screen.HomeScreen.route + "/{desk}",
                            arguments = listOf(
                                navArgument("desk") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                })
                        ) { backStackEntry ->
                            HomeScreen(
                                homeViewModel,
                                desk = backStackEntry.arguments?.getString("desk")
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeAuthState() {
        authViewModel.authenticationState.observe(this) {
            when (it) {
                AuthenticationState.UNAUTHENTICATED -> {
                    startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                    finish()
                }
                else -> println("Some Error Occured")
            }
        }
    }
}