package com.example.co_working.model.request

import retrofit2.http.Query

data class DeskAvailabilityRequestModel(
    val date : String,
    val slot_id : String,
    val type : String,
)
