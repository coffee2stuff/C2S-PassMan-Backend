package com.peteralexbizjak

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.peteralexbizjak.routes.auth
import com.peteralexbizjak.routes.database
import com.peteralexbizjak.services.AuthService
import com.peteralexbizjak.services.MongoService
import di.mongoModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit {
    startKoin {
        modules(mongoModule)
    }
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CORS) {
        //anyHost()
        host("localhost:3000", schemes = listOf("http", "https"))
    }

    install(StatusPages) {
        exception<Throwable> {
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf(
                    "code" to "500: Internal Server Error",
                    "message" to "This was not supposed to happen! This error was on us."
                )
            )
        }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
        }
    }

    routing {
        auth(authService, mongoService)
        database(mongoService)
    }
}

private val authService = AuthService()
private val mongoService = MongoService()
