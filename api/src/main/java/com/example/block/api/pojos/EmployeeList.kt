package com.example.block.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeList(@SerialName("employees") var employees: List<Employee>)