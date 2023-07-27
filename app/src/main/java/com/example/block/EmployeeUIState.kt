package com.example.block


sealed class EmployeeUIState<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): EmployeeUIState<T>(data)
    class Error<T>(data: T? = null, message: String? = "Some error occurred"): EmployeeUIState<T>(data, message)
    class Loading<T>(data: T? = null, message: String? = "Loading"): EmployeeUIState<T>(data, message)
}
