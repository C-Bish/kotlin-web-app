package org.bish

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlin.test.Test
import kotlin.test.assertEquals


class EventRouteTests {

    @Test
    fun testGetEvents() = testApplication {
        val response = client.get("/event")
        assert(
            Json.parseToJsonElement(response.body()).jsonArray.isNotEmpty()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }
    
    @Test
    fun testGetEvent() = testApplication {
        val response = client.get("/event/2222")
        assertEquals(
            """{"id":"2222","description":"Appointment","date":"2024-02-19T11:00"}""",
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAddEvent() = testApplication {
        // Post new event
        val putResponse = client.post("/event") {
            contentType(ContentType.Application.Json)
            setBody("""{"id":"111","description":"Birthday party","date":"2024-02-05T17:00:00"}""")
        }

        // Assertions on post response
        assertEquals("Event saved correctly", putResponse.bodyAsText())
        assertEquals(HttpStatusCode.Created, putResponse.status)

        // Get new event
        val getResponse = client.get("/event/111")
        assertEquals(HttpStatusCode.OK, getResponse.status)
    }

    @Test
    fun testDeleteEvent() = testApplication {

        // Get list of events
        val getResponse = client.get("/event")
        val events = Json.parseToJsonElement(getResponse.body()).jsonArray.size

        // Assertions on obtained list
        assertEquals(HttpStatusCode.OK, getResponse.status)

        // Delete event
        val deleteResponse = client.delete("/event/1111")

        // Assertions on delete response
        assertEquals("Event removed correctly", deleteResponse.bodyAsText())
        assertEquals(HttpStatusCode.Accepted, deleteResponse.status)

        // Get list of events
        val getResponse2 = client.get("/event")

        // Assertions on obtained list
        assertEquals(
            events - 1,
            Json.parseToJsonElement(getResponse2.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, getResponse2.status)
    }
}