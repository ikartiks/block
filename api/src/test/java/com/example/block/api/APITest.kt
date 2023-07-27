package com.example.block.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import retrofit2.Retrofit

class APITest: KoinTest {

    private val mockWebServer = MockWebServer()

    @BeforeEach
    fun setup() {
        mockWebServer.start(8080)
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }
    @Test
    fun testSuccessResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FileReader.readStringFromFile("success.json"))
        )

        runBlocking {
            val apiService = ApiService(getEndpointsForTests())
            val response = apiService.fetchEmployeeList()
            val employeeList = response.body()
            val employee = employeeList!!.employees[0]
            assertEquals(employee.fullName, "Justine Mason")
            assertEquals(employee.employeeType, "FULL_TIME")
        }
    }

    @Test
    fun testFailureResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(FileReader.readStringFromFile("fail.json"))
        )
        val apiService = ApiService(getEndpointsForTests())
        runBlocking {
            val response =apiService.fetchEmployeeList()
            Assertions.assertEquals(response.code(), 404)
        }
    }

    @Test
    fun testEmptyResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FileReader.readStringFromFile("empty.json"))
        )
        val apiService = ApiService(getEndpointsForTests())
        runBlocking {
            val response =apiService.fetchEmployeeList()
            val size = response.body()!!.employees.size
            assertEquals(size, 0)
        }
    }

    private fun getEndpointsForTests(): Endpoints {

        return Retrofit.Builder()
            .addConverterFactory(RetrofitInstance.getKotlinSerializationConverterFactory())
            .baseUrl("http://127.0.0.1:8080/")
            .client(RetrofitInstance.getClient())
            .build().create(Endpoints::class.java)
    }
}