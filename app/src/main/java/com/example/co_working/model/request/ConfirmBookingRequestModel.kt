package com.example.co_working.model.request

import com.google.gson.annotations.SerializedName

data class ConfirmBookingRequestModel(
    @SerializedName("date") val date : String,
    @SerializedName("slot_id") val slot_id : String,
    @SerializedName("workspace_id") val workspace_id : String,
    @SerializedName("type") val type : String,
)

