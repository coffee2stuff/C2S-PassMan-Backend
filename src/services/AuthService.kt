package com.peteralexbizjak.services

import com.peteralexbizjak.models.auth.LoginAuthModel
import com.peteralexbizjak.models.auth.SignUpAuthModel
import com.peteralexbizjak.models.auth.TokenAuthModel
import java.util.*
import java.util.regex.Pattern

class AuthService {

    fun loginService(login: LoginAuthModel): TokenAuthModel {
        return if (validateCredentials(login.email, login.password)) {
            TokenAuthModel(true, UUID.randomUUID().toString().replace("-", ""))
        } else {
            TokenAuthModel(false, "")
        }
    }

    fun signUpService(signUp: SignUpAuthModel): TokenAuthModel {
        return if (signUp.displayName.isNotEmpty() && validateCredentials(signUp.email, signUp.password)) {
            TokenAuthModel(true, UUID.randomUUID().toString().replace("-", ""))
        } else {
            TokenAuthModel(false, "")
        }
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