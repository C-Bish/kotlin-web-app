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

class TaskRouteTests {

    @Test
    fun testGetTasks() = testApplication {
        val response = client.get("/task")
        assert(
            Json.parseToJsonElement(response.body()).jsonArray.isNotEmpty()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetTask() = testApplication {
        val response = client.get("/task/1234")
        assertEquals(
            """{"id":"1234","description":"Finish API","dueDate":"2024-02-07T12:00"}""",
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAddTask() = testApplication {
        // Post new task
        val putResponse = client.post("/task") {
            contentType(ContentType.Application.Json)
            setBody("""{"id":"222","description":"Write more tests","dueDate":"2024-02-01T22:00:00"}""")
        }

        // Assertions on post response
        assertEquals("Task saved correctly", putResponse.bodyAsText())
        assertEquals(HttpStatusCode.Created, putResponse.status)

        // Get new task
        val getResponse = client.get("/task/222")
        assertEquals(HttpStatusCode.OK, getResponse.status)
    }

    @Test
    fun testDeleteTask() = testApplication {

        // Get list of tasks
        val getResponse = client.get("/task")
        val tasks = Json.parseToJsonElement(getResponse.body()).jsonArray.size

        // Assertions on obtained list
        assertEquals(HttpStatusCode.OK, getResponse.status)

        // Delete task
        val deleteResponse = client.delete("/task/6789")

        // Assertions on delete response
        assertEquals("Task removed correctly", deleteResponse.bodyAsText())
        assertEquals(HttpStatusCode.Accepted, deleteResponse.status)

        // Get list of tasks
        val getResponse2 = client.get("/task")

        // Assertions on obtained list
        assertEquals(
            tasks - 1,
            Json.parseToJsonElement(getResponse2.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, getResponse2.status)
    }
}