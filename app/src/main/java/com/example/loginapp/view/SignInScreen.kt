package com.example.loginapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginapp.R
import com.example.loginapp.viewmodels.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onSignInClick: (String, String) -> Unit,
    onSignUpClick: (String, String) -> Unit
) {
    val signInViewModel: SignInViewModel = viewModel()
    val email by signInViewModel.email.collectAsState()
    val password by signInViewModel.password.collectAsState()
    val emailErrorMessage by signInViewModel.emailErrorMessage.collectAsState()
    val passwordErrorMessage by signInViewModel.passwordErrorMessage.collectAsState()
    val isEmailError by signInViewModel.isEmailError.collectAsState()
    val isPasswordError by signInViewModel.isPasswordError.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isEmailError) {
            Text(
                text = emailErrorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        TextField(
            value = email,
            onValueChange = {
                signInViewModel.setEmail(it).toString()
            },
            label = { Text(text = stringResource(R.string.email_label_text)) },
            isError = isEmailError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isPasswordError) {
            Text(
                text = passwordErrorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        TextField(
            value = password,
            onValueChange = {
                signInViewModel.setPassword(it)
            },
            label = { Text(text = stringResource(R.string.password_label_text)) },
            isError = isPasswordError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = {
                if (signInViewModel.validateEmpty()) {
                    onSignInClick(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        TextButton(
            onClick = {
                if (signInViewModel.validateEmpty()) {
                    onSignUpClick(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}