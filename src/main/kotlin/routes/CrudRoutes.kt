package org.bish.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bish.models.Model

inline fun <reified T: Model> Route.crudRouting(storage: MutableList<T>, entity: String) {
    route("/$entity") {
        get {
            if (storage.isNotEmpty()) {
                call.respond(storage)
            } else {
                call.respondText("No $entity's found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val model = storage.find { it.id == id } ?: return@get call.respondText(
                "No $entity with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(model)
        }

        post {
            val model = call.receive<T>()
            storage.add(model)
            call.respondText("${entity.capitalize()} saved correctly", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (storage.removeIf { it.id == id }) {
                call.respondText("${entity.capitalize()} removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}