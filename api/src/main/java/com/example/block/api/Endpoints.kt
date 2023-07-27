package com.example.block.api

import com.example.block.api.pojos.EmployeeList
import retrofit2.Response
import retrofit2.http.GET

interface Endpoints {

    @GET("/sq-mobile-interview/employees.json")
    suspend fun getEmployeeDetails(): Response<EmployeeList>
}
