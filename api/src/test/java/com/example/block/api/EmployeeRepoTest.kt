package com.example.block.api

import com.example.block.api.pojos.EmployeeList
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class EmployeeRepoTest : BaseTest() {

    private val apiService: ApiService = mockk()

    private lateinit var employeeRepo: EmployeeRepo

    @BeforeEach
    fun setUp() {
        employeeRepo = EmployeeRepo(apiService)

        val successAsString = FileReader.readStringFromFile("success.json")
        val employeeList = Json.decodeFromString<EmployeeList>(successAsString)
        coEvery { apiService.fetchEmployeeList() } returns Response.success(employeeList)
    }

    @Test
    fun testSuccessfulFetch() {
        runBlocking {
            val employee = employeeRepo.fetchEmployeeList().data!!.employees[0]
            Assert.assertEquals(employee.fullName, "Justine Mason")
            Assert.assertEquals(employee.employeeType, "FULL_TIME")
        }
    }

    @Test
    fun testUnSuccessfulFetch() {
        coEvery { apiService.fetchEmployeeList() } returns Response.error(400, ResponseBody.create(null,"err"))
        runBlocking {
            val response = employeeRepo.fetchEmployeeList()
            assertNull(response.data)
            assertEquals(response.message, "Some error occurred")
        }
    }
}
