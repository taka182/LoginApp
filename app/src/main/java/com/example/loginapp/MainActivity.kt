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
                    val context = LocalContext.current as ComponentActivity
                    SignInScreen(
                        onSignInClick = { email, password ->
                            signIn(auth, email, password, context) { success ->
                                if (success) {
                                    navController.navigate("HomeScreen")
                                } else {
                                    Toast.makeText(context, "ログインに失敗しました", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        },
                        onSignUpClick = { email, password ->
                            signUp(auth, email, password, context) { success ->
                                if (success) {
                                    navController.navigate("HomeScreen")
                                } else {
                                    Toast.makeText(context, "新規登録に失敗しました", Toast.LENGTH_SHORT)
                                        .show()
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
        activity: ComponentActivity,
        onComplete: (Boolean) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                onComplete(task.isSuccessful)
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
        activity: ComponentActivity,
        onComplete: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                onComplete(task.isSuccessful)
            }
    }
}
