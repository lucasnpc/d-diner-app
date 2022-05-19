package com.example.ddinerapp.featureMain.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ddinerapp.featureMain.presentation.utils.Screen

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Scaffold {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column {
                OutlinedTextField(value = username, onValueChange = { username = it }, label = {
                    Text(
                        text = "E-mail"
                    )
                })
                OutlinedTextField(value = password, onValueChange = { password = it }, label = {
                    Text(
                        text = "Senha"
                    )
                })
                OutlinedButton(
                    onClick = { navController.navigate(Screen.DesksScreen.route) }, modifier = Modifier.align(
                        Alignment.End
                    )
                ) {
                    Text(text = "Log In")
                }
            }
        }
    }
}