package com.example.co_working.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("email") val emailORMobile : String,
    @SerializedName("password") val password : String
)
