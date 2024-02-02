package org.bish.routes

import io.ktor.server.routing.*
import org.bish.models.Reminder
import org.bish.models.reminderStorage

/*
* Defines CRUD operations for the Reminder domain
*/
fun Route.reminderRouting() {
    crudRouting<Reminder>(reminderStorage,"reminder")
}