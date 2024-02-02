package org.bish.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Event(override val id: String, val description: String, val date: LocalDateTime) : Model

val eventStorage = mutableListOf(
    Event(
        id = "1111",
        description = "Production release",
        date = LocalDateTime(2024, 2, 7, 21, 0, 0)
    ),
    Event(
        id = "2222",
        description = "Appointment",
        date = LocalDateTime(2024, 2, 19, 11, 0)
    )
)