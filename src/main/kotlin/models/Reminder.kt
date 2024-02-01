package org.bish.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Reminder(val id: String, val description: String, val date: LocalDateTime)

val reminderStorage = mutableListOf(
    Reminder(
        id = "1234",
        description = "Take the washing out",
        date = LocalDateTime(2024, 2, 7, 20, 29, 32)
    ),
    Reminder(
        id = "6789",
        description = "Finish creating this class",
        date = LocalDateTime(2024, 2, 21, 10, 43)
    )
)