package org.bish.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bish.models.Reminder
import org.bish.models.reminderStorage

/*
* Defines CRUD operations for the Reminder domain
*/
fun Route.reminderRouting() {
    route("/reminder") {
        get {
            if (reminderStorage.isNotEmpty()) {
                call.respond(reminderStorage)
            } else {
                call.respondText("No reminders found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                reminderStorage.find { it.id == id } ?: return@get call.respondText(
                    "No reminder with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }

        post {
            val reminder = call.receive<Reminder>()
            reminderStorage.add(reminder)
            call.respondText("Reminder stored correctly", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (reminderStorage.removeIf { it.id == id }) {
                call.respondText("Reminder removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}