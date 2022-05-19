package com.example.ddinerapp.featureMain.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.featureMain.presentation.cart.CartScreen
import com.example.ddinerapp.featureMain.presentation.desks.DesksScreen
import com.example.ddinerapp.featureMain.presentation.home.HomeScreen
import com.example.ddinerapp.featureMain.presentation.login.LoginScreen
import com.example.ddinerapp.featureMain.presentation.notification.NotificationScreen
import com.example.ddinerapp.featureMain.presentation.orders.OrdersScreen
import com.example.ddinerapp.featureMain.presentation.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                    NavHost(
                        navController = navController, startDestination = Screen.LoginScreen.route
                    ) {
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController = navController)
                        }
                        composable(route = Screen.DesksScreen.route) {
                            DesksScreen(navController = navController)
                        }
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.OrdersScreen.route) {
                            OrdersScreen(navController = navController)
                        }
                        composable(route = Screen.CartScreen.route) {
                            CartScreen(navController = navController)
                        }
                        composable(route = Screen.NotificationScreen.route) {
                            NotificationScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}