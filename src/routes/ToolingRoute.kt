package com.peteralexbizjak.routes

import com.peteralexbizjak.models.tooling.NumericUuidModel
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import utils.helpers.generateNumericUUID

fun Route.tooling() {
    route("uuid") {
        get("unique_numeric_uuir") {
            call.respond(HttpStatusCode.OK, NumericUuidModel(generateNumericUUID()))
        }
    }
}