package com.peteralexbizjak.routes

import com.peteralexbizjak.models.auth.ErrorAuthModel
import com.peteralexbizjak.models.query.IDQueryModel
import com.peteralexbizjak.services.MongoService
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import models.database.LoginModel

fun Route.database(mongoService: MongoService) {
    route("login") {
        post("create") {
            val request = call.receive<LoginModel>()
            val dbOperationResult = mongoService.createLogin(request)
            dbResultCallRespond(call, dbOperationResult)
        }

        get("single") {
            val queryParameters = call.request.queryParameters
            val queryParameterID = queryParameters["id"] ?: ""
            val queryParameterToken = queryParameters["token"] ?: ""
            if (queryParameterID.isNotBlank() && queryParameterToken.isNotBlank()) {
                val dbOperationResult = mongoService.fetchLogin(queryParameterID, queryParameterToken)
                call.respond(HttpStatusCode.OK, dbOperationResult)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorAuthModel(
                        400,
                        "400: Bad Request",
                        "Your request does not contain necessary parameters 'id' and 'token'"
                    )
                )
            }
        }

        get("all") {
            val queryParameters = call.request.queryParameters
            val queryParameterToken = queryParameters["token"] ?: ""
            if (queryParameterToken.isNotBlank()) {
                val dbOperationResult = mongoService.fetchAllLogins(queryParameterToken)
                call.respond(HttpStatusCode.OK, dbOperationResult)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorAuthModel(
                        400,
                        "400: Bad Request",
                        "Your request does not contain necessary parameter 'token'"
                    )
                )
            }
        }

        patch("update") {
            val request = call.receive<LoginModel>()
            val dbOperationResult = mongoService.updateLogin(request)
            dbResultCallRespond(call, dbOperationResult)
        }

        delete("delete") {
            val request = call.receive<IDQueryModel>()
            val dbOperationResult = mongoService.deleteLoginById(request.documentID)
            dbResultCallRespond(call, dbOperationResult)
        }
    }
}

private suspend fun dbResultCallRespond(call: ApplicationCall, wasSuccessful: Boolean) {
    if (wasSuccessful) {
        call.respond(HttpStatusCode.OK, mapOf("code" to 200, "status" to "Database operation was successful"))
    } else {
        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorAuthModel(500, "500: Internal Server Error", "Unable to perform the database operation")
        )
    }
}