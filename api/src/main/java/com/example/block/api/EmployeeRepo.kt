package com.example.block.api

import com.example.block.api.pojos.EmployeeList

class EmployeeRepo(private val apiService: ApiService) {

    suspend fun fetchEmployeeList(): APIResponse<EmployeeList> {
        val response = apiService.fetchEmployeeList()
        return if(response.isSuccessful){
            APIResponse.Success(response.body()!!)
        }else{
            APIResponse.Error(null, "Some error occurred")
        }
    }
}