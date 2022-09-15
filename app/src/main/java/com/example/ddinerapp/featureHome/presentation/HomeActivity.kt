package com.example.ddinerapp.featureHome.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.databinding.ActivityHomeBinding
import com.example.ddinerapp.featureHome.presentation.cart.CartScreen
import com.example.ddinerapp.featureHome.presentation.makeYourPizza.MakeYourPizzaScreen
import com.example.ddinerapp.featureHome.presentation.orderingItems.OrderingItemsScreen
import com.example.ddinerapp.featureHome.presentation.orderingMenu.OrderingMenuScreen
import com.example.ddinerapp.featureHome.presentation.orders.OrdersScreen
import com.example.ddinerapp.featureHome.presentation.util.HomeScreen
import com.example.ddinerapp.featureHome.presentation.util.BottomNavItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val screens = listOf(
            BottomNavItem.MenuItem,
            BottomNavItem.CartItem,
            BottomNavItem.OrdersItem,
        )
        binding.homeScreen.setContent {
            val navController = rememberNavController()

            DDinerAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController, screens)
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreen.OrderingMenuScreen.route,
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFFFFFF),
                                        Color(0xFFC4C4C4)
                                    )
                                )
                            )
                    ) {
                        composable(route = HomeScreen.OrderingMenuScreen.route) {
                            OrderingMenuScreen(navController = navController)
                        }
                        composable(
                            route = HomeScreen.OrderingItemsScreen.route + "/{itemCategory}",
                            arguments = listOf(navArgument("itemCategory") {
                                type = NavType.StringType
                                defaultValue = ""
                            })
                        ) { backStackEntry ->
                            OrderingItemsScreen(
                                navController = navController,
                                backStackEntry.arguments?.getString("itemCategory").toString(),
                            )
                        }
                        composable(route = HomeScreen.CartScreen.route) {
                            CartScreen()
                        }
                        composable(route = HomeScreen.OrdersScreen.route) {
                            OrdersScreen()
                        }
                        composable(route = HomeScreen.MakeYourPizzaScreen.route) {
                            MakeYourPizzaScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    navController: NavHostController,
    screens: List<BottomNavItem>
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.contentDescription,
                        tint = MaterialTheme.colors.onSurface
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}