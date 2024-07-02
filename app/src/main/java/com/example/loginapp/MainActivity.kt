package com.example.loginapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginapp.view.HomeScreen
import com.example.loginapp.view.SignInScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            val navController = rememberNavController()
            val startDestination = if (auth.currentUser == null) "SignInScreen" else "HomeScreen"
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(route = "SignInScreen") {
                    val context = LocalContext.current
                    SignInScreen(
                        onSignInClick = { email, password ->
                            signIn(auth, email, password) { success ->
                                if (success) {
                                    navController.navigate("HomeScreen")
                                } else {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.failed_sign_In_message),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        onSignUpClick = { email, password ->
                            signUp(auth, email, password) { success ->
                                if (success) {
                                    navController.navigate("HomeScreen")
                                } else {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.failed_sign_up_message),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    )
                }

                composable(route = "HomeScreen") {
                    HomeScreen(
                        onSignOutClick = {
                            signOut(auth)
                            navController.navigate("SignInScreen")
                        }
                    )
                }
            }
        }
    }

    //サインインするメソッド
    private fun signIn(
        auth: FirebaseAuth,
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                onComplete(it.isSuccessful)
            }
    }

    //サインアウトするメソッド
    private fun signOut(
        auth: FirebaseAuth
    ) {
        auth.signOut()
    }

    //新規登録をするメソッド
    private fun signUp(
        auth: FirebaseAuth,
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                onComplete(it.isSuccessful)
            }
    }
}
