package com.example.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.loginapp.ui.theme.LoginAppTheme
import com.example.loginapp.view.LoginScreen
import com.example.loginapp.view.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            LoginAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isLoggedIn by remember { mutableStateOf(auth.currentUser != null) }

                    if (isLoggedIn) {
                        HomeScreen()
                    } else {
                        val context = LocalContext.current as ComponentActivity
                        LoginScreen(
                            onLoginClick = { email, password ->
                                signIn(auth, email, password, context) { success ->
                                    if (success) isLoggedIn = true
                                }
                            },
                            onRegisterClick = { email, password ->
                                register(auth, email, password, context) { success ->
                                    if (success) isLoggedIn = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    //ログインするメソッド
    private fun signIn(
        auth: FirebaseAuth,
        email: String,
        password: String,
        activity: ComponentActivity,
        onComplete: (Boolean) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                onComplete(task.isSuccessful)
            }
    }

    //新規登録をするメソッド
    private fun register(
        auth: FirebaseAuth,
        email: String,
        password: String,
        activity: ComponentActivity,
        onComplete: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                onComplete(task.isSuccessful)
            }
    }
}
