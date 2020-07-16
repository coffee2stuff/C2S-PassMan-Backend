package com.peteralexbizjak

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.features.*
import com.fasterxml.jackson.databind.*
import com.peteralexbizjak.routes.auth
import com.peteralexbizjak.routes.database
import com.peteralexbizjak.services.AuthService
import com.peteralexbizjak.services.MongoService
import di.mongoModule
import io.ktor.jackson.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
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
