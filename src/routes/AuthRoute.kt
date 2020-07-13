package com.peteralexbizjak.routes

import com.peteralexbizjak.models.auth.LoginAuthModel
import com.peteralexbizjak.models.auth.SignUpAuthModel
import com.peteralexbizjak.services.AuthService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.auth(service: AuthService) {
    val httpStatusCodeOK = HttpStatusCode.OK
    val httpStatusCodeBadReq = HttpStatusCode.BadRequest

    route("auth") {
        post("login") {
            val login = call.receive<LoginAuthModel>()
            val loginResponse = service.loginService(login)
            println("Received call to auth/login: $login, responding with $loginResponse")
            call.respond(
                if (loginResponse.wasSuccessful) httpStatusCodeOK else httpStatusCodeBadReq,
                loginResponse
            )
        }

        post("sign_up") {
            val signUp = call.receive<SignUpAuthModel>()
            val signUpResponse = service.signUpService(signUp)
            println("Received call to auth/sign_up, responding with $signUpResponse")
            call.respond(
                if (signUpResponse.wasSuccessful) httpStatusCodeOK else httpStatusCodeBadReq,
                signUpResponse
            )
        }
    }
}