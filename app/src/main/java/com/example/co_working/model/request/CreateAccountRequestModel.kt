package com.example.co_working.model.request

import com.google.gson.annotations.SerializedName

data class CreateAccountRequestModel(
    @SerializedName("email") val emailORMobile : String,
    @SerializedName("name") val name : String
)
