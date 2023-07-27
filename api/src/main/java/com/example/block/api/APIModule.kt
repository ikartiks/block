package com.example.block.api

import org.koin.dsl.module

val apiModule = module {
    single { provideEndpoints() }
    single { ApiService(get()) }
    single { EmployeeRepo(get()) }
}

fun provideEndpoints(): Endpoints {
        return RetrofitInstance.provideEndpoints()
}
