package com.example.block.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee(@SerialName("uuid")
                    var uuid: String,

    @SerialName("full_name")
var fullName: String,

@SerialName("phone_number")
var phoneNumber: String,

@SerialName("email_address")
var emailAddress: String,

@SerialName("biography")
var biography: String,

@SerialName("photo_url_small")
var photoUrlSmall: String,

@SerialName("photo_url_large")
var photoUrlLarge: String,

@SerialName("team")
var team: String,

@SerialName("employee_type")
var employeeType: String? = null)