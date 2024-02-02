package org.bish.routes

import io.ktor.server.routing.*
import org.bish.models.Event
import org.bish.models.eventStorage

/*
* Defines CRUD operations for the Event domain
*/
fun Route.eventRouting() {
    crudRouting<Event>(eventStorage,"event")
}