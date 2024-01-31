package org.bish.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf(
    Customer(
        id = "1234",
        firstName = "C",
        lastName = "B",
        email = "test@gmail.com"
    ),
    Customer(
        id = "6789",
        firstName = "C",
        lastName = "D",
        email = "test2@gmail.com"
    )
)