package com.example.ddinerapp.featureMain.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.common.util.AuthenticationState
import com.example.ddinerapp.featureMain.presentation.home.HomeScreen
import com.example.ddinerapp.featureMain.presentation.login.LoginScreen
import com.example.ddinerapp.featureMain.presentation.login.LoginViewModel
import com.example.ddinerapp.featureMain.presentation.orderingDelivery.OrderingDeliveryScreen
import com.example.ddinerapp.featureMain.presentation.orderingDesks.OrderingDesksScreen
import com.example.ddinerapp.featureMain.presentation.orderingType.OrderingTypeScreen
import com.example.ddinerapp.featureMain.presentation.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DDinerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    observeAuthState(navController)
                    NavHost(
                        navController = navController, startDestination = Screen.LoginScreen.route
                    ) {
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen()
                        }
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
                                desk = backStackEntry.arguments?.getString("desk")
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeAuthState(navController: NavHostController) {
        viewModel.authenticationState.observe(this) {
            when (it) {
                AuthenticationState.AUTHENTICATED -> {
                    navController.navigate(Screen.OrderingTypeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
                AuthenticationState.UNAUTHENTICATED -> {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.OrderingTypeScreen.route) {
                            inclusive = true
                        }
                    }
                }
                else -> println("Some Error Occured")
            }
        }
    }
}