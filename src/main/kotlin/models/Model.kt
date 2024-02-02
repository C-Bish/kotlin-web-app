package org.bish.models

import kotlinx.serialization.Serializable

/* Interface used for all models */
@Serializable
sealed interface Model {
    val id: String
}