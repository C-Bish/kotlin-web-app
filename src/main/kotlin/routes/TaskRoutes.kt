package org.bish.routes

import io.ktor.server.routing.*
import org.bish.models.Task
import org.bish.models.taskStorage

/*
* Defines CRUD operations for the Task domain
*/
fun Route.taskRouting() {
    crudRouting<Task>(taskStorage,"task")
}