package com.peteralexbizjak.routes

import com.peteralexbizjak.models.auth.ErrorAuthModel
import com.peteralexbizjak.models.database.AllDatabaseItemsModel
import com.peteralexbizjak.models.query.IDQueryModel
import com.peteralexbizjak.services.MongoService
import com.peteralexbizjak.utils.helpers.concatenate
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import models.database.LoginModel
import models.database.NoteModel
import models.database.UserModel

fun Route.database(mongoService: MongoService) {
    route("all") {
        get("per_user") {
            val queryParameters = call.request.queryParameters
            val queryParameterToken = queryParameters["token"] ?: ""
            if (queryParameterToken.isNotBlank()) {
                val logins = mongoService
                        .fetchAllLogins(queryParameterToken)
                        .map { AllDatabaseItemsModel(it.modelType(), it.id, it.loginName, it.username) }
                val notes = mongoService
                        .fetchAllNotes(queryParameterToken)
                        .map { AllDatabaseItemsModel(it.modelType(), it.id, "Secure note", "${it.noteContents.substring(0..25)}...") }
                val dbOperationResult = concatenate(logins, notes)
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
    }
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

    route("note") {
        post("create") {
            val request = call.receive<NoteModel>()
            val dbOperationResult = mongoService.createNote(request)
            dbResultCallRespond(call, dbOperationResult)
        }

        get("single") {
            val queryParameters = call.request.queryParameters
            val queryParameterID = queryParameters["id"] ?: ""
            val queryParameterToken = queryParameters["token"] ?: ""
            if (queryParameterID.isNotBlank() && queryParameterToken.isNotBlank()) {
                val dbOperationResult = mongoService.fetchNote(queryParameterID, queryParameterToken)
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
                val dbOperationResult = mongoService.fetchAllNotes(queryParameterToken)
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
            val request = call.receive<NoteModel>()
            val dbOperationResult = mongoService.updateNote(request)
            dbResultCallRespond(call, dbOperationResult)
        }

        delete("delete") {
            val request = call.receive<IDQueryModel>()
            val dbOperationResult = mongoService.deleteNoteById(request.documentID)
            dbResultCallRespond(call, dbOperationResult)
        }
    }

    route("user") {
        post("create") {
            val request = call.receive<UserModel>()
            val dbOperationResult = mongoService.createUser(request)
            dbResultCallRespond(call, dbOperationResult)
        }

        get("single_by_id") {
            val queryParameters = call.request.queryParameters
            val queryParameterID = queryParameters["id"] ?: ""
            if (queryParameterID.isNotBlank()) {
                val dbOperationResult = mongoService.fetchUser(true, queryParameterID)
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

        get("single_by_email_password") {
            val queryParameters = call.request.queryParameters
            val queryParameterEmail = queryParameters["email"] ?: ""
            val queryParameterPass = queryParameters["password"] ?: ""
            if (queryParameterEmail.isNotBlank() && queryParameterPass.isNotBlank()) {
                val dbOperationResult = mongoService.fetchUser(false, queryParameterEmail, queryParameterPass)
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

        patch("update") {
            val request = call.receive<UserModel>()
            val dbOperationResult = mongoService.updateUser(request)
            dbResultCallRespond(call, dbOperationResult)
        }

        delete("delete") {
            val request = call.receive<IDQueryModel>()
            val dbOperationResult = mongoService.deleteUserById(request.documentID)
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