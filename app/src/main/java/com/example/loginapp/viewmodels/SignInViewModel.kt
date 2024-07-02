package com.example.loginapp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isEmailError = MutableStateFlow(false)
    val isEmailError: StateFlow<Boolean> = _isEmailError

    private val _isPasswordError = MutableStateFlow(false)
    val isPasswordError: StateFlow<Boolean> = _isPasswordError

    private val _emailErrorMessage = MutableStateFlow("")
    val emailErrorMessage: StateFlow<String> = _emailErrorMessage

    private val _passwordErrorMessage = MutableStateFlow("")
    val passwordErrorMessage: StateFlow<String> = _passwordErrorMessage

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    private fun setEmailErrorMessage(emailErrorMessage: String) {
        _emailErrorMessage.value = emailErrorMessage
    }

    private fun setPasswordErrorMessage(passwordErrorMessage: String) {
        _passwordErrorMessage.value = passwordErrorMessage
    }

    fun validateEmpty(): Boolean {
        var isValid = true

        if (_email.value.isEmpty()) {
            _isEmailError.value = true
            setEmailErrorMessage("メールアドレスを入力してください")
            isValid = false
        } else {
            _isEmailError.value = false
            setEmailErrorMessage("")
        }

        if (_password.value.isEmpty()) {
            _isPasswordError.value = true
            setPasswordErrorMessage("パスワードを入力してください")
            isValid = false
        } else {
            _isPasswordError.value = false
            setPasswordErrorMessage("")
        }

        return isValid
    }
}