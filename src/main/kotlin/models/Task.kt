package org.bish.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: String, val description: String, val dueDate: LocalDateTime)

val taskStorage = mutableListOf(
    Task(
        id = "1234",
        description = "Finish API",
        dueDate = LocalDateTime(2024, 2, 7, 12, 0)
    ),
    Task(
        id = "6789",
        description = "Create domain specific services",
        dueDate = LocalDateTime(2024, 2, 4, 0, 0)
    )
)