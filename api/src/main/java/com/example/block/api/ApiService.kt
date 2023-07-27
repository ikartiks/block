package com.example.block.api

class ApiService(private var endpoints: Endpoints) {

    suspend fun fetchEmployeeList() = endpoints.getEmployeeDetails()
}
