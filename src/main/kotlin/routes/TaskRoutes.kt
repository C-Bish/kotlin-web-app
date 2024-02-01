package org.bish.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bish.models.Task
import org.bish.models.taskStorage

/*
* Defines CRUD operations for the Task domain
*/
fun Route.taskRouting() {
    route("/task") {
        get {
            if (taskStorage.isNotEmpty()) {
                call.respond(taskStorage)
            } else {
                call.respondText("No tasks found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val task = taskStorage.find { it.id == id } ?: return@get call.respondText(
                "No task with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(task)
        }

        post {
            val task = call.receive<Task>()
            taskStorage.add(task)
            call.respondText("Task saved correctly", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (taskStorage.removeIf { it.id == id }) {
                call.respondText("Task removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}