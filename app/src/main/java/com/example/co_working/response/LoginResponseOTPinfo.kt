package com.example.co_working.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseOTPinfo(
    @SerializedName("ID") val iD: Int,
    @SerializedName("TransactionMode") val transactionMode: String,
    @SerializedName("OTPValidTill") val oTPValidTill: String,
    @SerializedName("OTP") val oTP: String
) : Serializable
