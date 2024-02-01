package org.bish

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bish.routes.reminderRouting
import org.bish.routes.taskRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSerialization()
    install(CORS) {
        anyHost()
    }
}

private fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, world!", ContentType.Text.Html)
        }
        reminderRouting()
        taskRouting()
    }
}

private fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
