package com.peteralexbizjak.routes

import com.peteralexbizjak.models.auth.ErrorAuthModel
import com.peteralexbizjak.models.auth.LoginAuthModel
import com.peteralexbizjak.models.auth.SignUpAuthModel
import com.peteralexbizjak.models.auth.TokenAuthModel
import com.peteralexbizjak.services.AuthService
import com.peteralexbizjak.services.MongoService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import models.database.UserModel
import utils.helpers.generateNumericUUID
import java.util.*

fun Route.auth(service: AuthService, mongoService: MongoService) {

    route("auth") {
        post("login") {
            val login = call.receive<LoginAuthModel>()
            val loginResponse = service.loginService(login)
            if (loginResponse) {
                val user = mongoService.fetchUser(false, login.email, login.password)
                if (user !== null) {
                    call.respond(HttpStatusCode.OK, TokenAuthModel(true, user.accessToken))
                } else {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        ErrorAuthModel(401, "401: Unauthorized", "This user does not exist in our database")
                    )
                }
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorAuthModel(400, "400: Bad Request", "Provided credentials were in invalid format")
                )
            }
        }

        post("sign_up") {
            val signUp = call.receive<SignUpAuthModel>()
            val signUpResponse = service.signUpService(signUp)
            if (signUpResponse) {
                val accessToken = UUID.randomUUID().toString()
                val userCreationResponse = mongoService.createUser(
                    UserModel(
                        generateNumericUUID(),
                        accessToken,
                        signUp.displayName,
                        signUp.email,
                        signUp.password
                    )
                )
                if (userCreationResponse) {
                    call.respond(HttpStatusCode.OK, TokenAuthModel(true, accessToken))
                } else {
                    call.respond(
                        HttpStatusCode.ServiceUnavailable,
                        ErrorAuthModel(
                            503,
                            "503: Service Unavailable",
                            "We were unable to store the user to our database."
                        )
                    )
                }
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorAuthModel(400, "400: Bad Request", "Provided credentials were in invalid format")
                )
            }
        }
    }
}