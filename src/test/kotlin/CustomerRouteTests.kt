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

class CustomerRouteTests {

    @Test
    fun testGetCustomers() = testApplication {
        val response = client.get("/customer")
        assertEquals(
            2,
            Json.parseToJsonElement(response.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }
    
    @Test
    fun testGetCustomer() = testApplication {
        val response = client.get("/customer/1234")
        assertEquals(
            """{"id":"1234","firstName":"C","lastName":"B","email":"test@gmail.com"}""",
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAddCustomer() = testApplication {
        // Post new customer
        val putResponse = client.post("/customer") {
            contentType(ContentType.Application.Json)
            setBody("""{"id":"111","firstName":"First","lastName":"Last","email":"mail@mail.com"}""")
        }

        // Assertions on post response
        assertEquals("Customer stored correctly", putResponse.bodyAsText())
        assertEquals(HttpStatusCode.Created, putResponse.status)

        // Get list of customers
        val getResponse = client.get("/customer")

        // Assertions on obtained list
        assertEquals(
            3,
            Json.parseToJsonElement(getResponse.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, getResponse.status)
    }

    @Test
    fun testDeleteCustomer() = testApplication {

        // Get list of customers
        val getResponse = client.get("/customer")

        // Assertions on obtained list
        assertEquals(
            3,
            Json.parseToJsonElement(getResponse.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, getResponse.status)

        // Delete customer
        val deleteResponse = client.delete("/customer/6789")

        // Assertions on delete response
        assertEquals("Customer removed correctly", deleteResponse.bodyAsText())
        assertEquals(HttpStatusCode.Accepted, deleteResponse.status)

        // Get list of customers
        val getResponse2 = client.get("/customer")

        // Assertions on obtained list
        assertEquals(
            2,
            Json.parseToJsonElement(getResponse2.body()).jsonArray.size
        )
        assertEquals(HttpStatusCode.OK, getResponse2.status)
    }
}