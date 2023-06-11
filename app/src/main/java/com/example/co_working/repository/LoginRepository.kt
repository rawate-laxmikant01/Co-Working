package com.example.co_working.repository

import com.example.co_working.model.request.ConfirmBookingRequestModel
import com.example.co_working.model.request.CreateAccountRequestModel
import com.example.co_working.model.request.DeskAvailabilityRequestModel
import com.example.co_working.model.request.LoginRequestModel
import com.example.co_working.model.response.*
import com.example.co_working.webapi.ApiService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getLogin(dataModelLoginBody: LoginRequestModel): Response<LoginResponseModel> {
        return apiService.getLogin(dataModelLoginBody)
    }

    suspend fun createAccount(createAccountRequestModel: CreateAccountRequestModel): Response<LoginResponseModel> {
        return apiService.createAccount(createAccountRequestModel)
    }
    suspend fun confirmBooking(confirmBookingRequestModel: ConfirmBookingRequestModel): Response<ConfirmBookingResponseModel> {
        return apiService.confirmBooking(confirmBookingRequestModel)
    }

    suspend fun getSlots(date: String):Response<SlotResponseModel> {
        return apiService.getSlots(date)
    }

    suspend fun getBookingHistorys(user_id: String):Response<BookingHistoryResponseModel> {
        return apiService.getBookingHistorys(user_id)
    }

    suspend fun getDesks(deskAvailabilityRequestModel: DeskAvailabilityRequestModel):Response<DeskResponseModel> {
        return apiService.getDesks(date = deskAvailabilityRequestModel.date, slot_id = deskAvailabilityRequestModel.slot_id, type = deskAvailabilityRequestModel.type)
    }
}