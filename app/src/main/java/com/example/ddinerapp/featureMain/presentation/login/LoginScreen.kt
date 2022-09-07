package com.example.ddinerapp.featureMain.presentation.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddinerapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

@Composable
fun LoginScreen() {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = { }
    )
    Image(
        painter = painterResource(id = R.drawable.restaurant),
        contentDescription = "Restaurant Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Column {
            Button(
                onClick = {
                    openSignInTools(signInLauncher)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                ),
                modifier = Modifier.width(250.dp)
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
                ),
                modifier = Modifier.width(250.dp)
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

fun openSignInTools(
    signInLauncher: ManagedActivityResultLauncher<Intent, FirebaseAuthUIAuthenticationResult>,
    google: Boolean = false
) {
    signInLauncher.launch(AuthUI.getInstance().createSignInIntentBuilder().apply {
        if (google) setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
    }.build())
}