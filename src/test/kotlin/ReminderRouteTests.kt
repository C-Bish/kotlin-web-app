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

class ReminderRouteTests {

    @Test
    fun testGetReminders() = testApplication {
        val response = client.get("/reminder")
        assert(
            Json.parseToJsonElement(response.body()).jsonArray.isNotEmpty()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }
    
    @Test
    fun testGetReminder() = testApplication {
        val response = client.get("/reminder/1234")
        assertEquals(
            """{"id":"1234","description":"Take the washing out","date":"2024-02-07T20:29:32"}""",
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAddReminder() = testApplication {
        // Post new reminder
        val putResponse = client.post("/reminder") {
            contentType(ContentType.Application.Json)
            setBody("""{"id":"111","description":"Complete testing","date":"2024-02-05T21:00:00"}""")
        }

        // Assertions on post response
        assertEquals("Reminder stored correctly", putResponse.bodyAsText())
        assertEquals(HttpStatusCode.Created, putResponse.status)

        // Get new reminder
        val getResponse = client.get("/reminder/111")
        assertEquals(HttpStatusCode.OK, getResponse.status)
    }

    @Test
    fun testDeleteReminder() = testApplication {

        // Get list of reminders
        val getResponse = client.get("/reminder")
        val reminders = Json.parseToJsonElement(getResponse.body()).jsonArray.size

        // Assertions on obtained list
        assertEquals(HttpStatusCode.OK, getResponse.status)

        // Delete reminder
        val deleteResponse = client.delete("/reminder/6789")

        // Assertions on delete response
        assertEquals("Reminder removed correctly", deleteResponse.bodyAsText())
        assertEquals(HttpStatusCode.Accepted, deleteResponse.status)

        // Get list of reminders
        val getResponse2 = client.get("/reminder")

        // Assertions on obtained list
        assertEquals(
            reminders - 1,
            Json.parseToJsonElement(getResponse2.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, getResponse2.status)
    }
}