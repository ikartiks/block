package com.example.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.block.api.APIResponse
import com.example.block.api.EmployeeRepo
import com.example.block.api.pojos.EmployeeList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class EmployeeViewModel(private val employeeRepo: EmployeeRepo) : ViewModel(), KoinComponent {

    val employeeFlow = MutableStateFlow<EmployeeUIState<EmployeeList>>(EmployeeUIState.Loading())
    fun fetchLatestData() {
        employeeFlow.value = EmployeeUIState.Loading()
        viewModelScope.launch {
            delay(200)
            when (val response = employeeRepo.fetchEmployeeList()) {
                is APIResponse.Success -> {
                    employeeFlow.value = EmployeeUIState.Success(response.data!!)
                }

                is APIResponse.Error -> {
                    employeeFlow.value = EmployeeUIState.Error()
                }
            }
        }
    }
}
