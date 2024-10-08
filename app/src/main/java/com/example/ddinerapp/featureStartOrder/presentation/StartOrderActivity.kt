package com.example.ddinerapp.featureStartOrder.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ddinerapp.R
import com.example.ddinerapp.common.theme.DDinerAppTheme
import com.example.ddinerapp.common.util.observeAuthState
import com.example.ddinerapp.databinding.ActivityMainBinding
import com.example.ddinerapp.featureStartOrder.presentation.orderingDelivery.OrderingDeliveryScreen
import com.example.ddinerapp.featureStartOrder.presentation.orderingDesks.OrderingDesksScreen
import com.example.ddinerapp.featureStartOrder.presentation.orderingType.OrderingTypeScreen
import com.example.ddinerapp.featureStartOrder.presentation.utils.Screen
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartOrderActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val startOrderViewModel: StartOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeAuthState { logout ->
            if (logout)
                startOrderViewModel.clearPreferences()
        }

        binding.mainScreen.setContent {
            DDinerAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    fontSize = 20.sp
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = { /*TODO*/ }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Notifications,
                                        contentDescription = "Notification",
                                        tint = MaterialTheme.colors.onPrimary,
                                    )
                                }
                                IconButton(
                                    onClick = { AuthUI.getInstance().signOut(this@StartOrderActivity) },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Logout,
                                        contentDescription = "Logout",
                                        tint = MaterialTheme.colors.onPrimary,
                                    )
                                }
                            }
                        )
                    }
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.OrderingTypeScreen.route,
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
                        composable(route = Screen.OrderingTypeScreen.route) {
                            OrderingTypeScreen(navController = navController)
                        }
                        composable(route = Screen.OrderingDesksScreen.route) {
                            OrderingDesksScreen()
                        }
                        composable(route = Screen.OrderingDeliveryScreen.route) {
                            OrderingDeliveryScreen()
                        }
                    }
                }
            }
        }
    }
}