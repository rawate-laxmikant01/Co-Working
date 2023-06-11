package com.example.co_working.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("user_id") val status: Int,
    @SerializedName("message") val message: String
)
