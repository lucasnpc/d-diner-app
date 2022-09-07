package com.example.ddinerapp.featureMain.presentation.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddinerapp.R
import com.example.ddinerapp.featureMain.presentation.utils.Screen
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

@Composable
fun LoginScreen(navController: NavController) {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
            }
        }
    )

    Scaffold(backgroundColor = Color.LightGray) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Column {
                Button(
                    onClick = {
                        openSignInTools(signInLauncher)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Icon(
                        Icons.Outlined.Mail,
                        contentDescription = "Email Icon",
                        modifier = Modifier.size(25.dp),
                        tint = Color.White
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "Sign in with email", style = TextStyle(
                            color = Color.White, fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {
                        openSignInTools(signInLauncher, google = true)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Gray
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google__g__logo),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = "Sign in with Google", style = TextStyle(
                            color = Color.Gray, fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

fun openSignInTools(
    signInLauncher: ManagedActivityResultLauncher<Intent, FirebaseAuthUIAuthenticationResult>,
    google: Boolean = false
) {
    signInLauncher.launch(AuthUI.getInstance().createSignInIntentBuilder().apply {
        if (google) setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
    }.build())
}