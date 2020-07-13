package com.peteralexbizjak.services

import com.peteralexbizjak.models.auth.LoginAuthModel
import com.peteralexbizjak.models.auth.SignUpAuthModel
import java.util.regex.Pattern

class AuthService {

    fun loginService(login: LoginAuthModel): Boolean {
        return validateCredentials(login.email, login.password)
    }

    fun signUpService(signUp: SignUpAuthModel): Boolean {
        return signUp.displayName.isNotEmpty() && validateCredentials(signUp.email, signUp.password)
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    private fun isEmailValid(email: String): Boolean = Pattern
        .compile("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        .matcher(email)
        .matches()

    private fun isPasswordValid(password: String): Boolean = Pattern
        .compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")
        .matcher(password)
        .matches()
}