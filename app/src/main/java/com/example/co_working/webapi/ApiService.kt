package com.example.co_working.webapi

import com.example.co_working.model.request.ConfirmBookingRequestModel
import com.example.co_working.model.request.CreateAccountRequestModel
import com.example.co_working.model.request.LoginRequestModel
import com.example.co_working.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //login
    @POST("login")
    suspend fun getLogin(
        @Body dataModelLoginBody: LoginRequestModel
    ): Response<LoginResponseModel>

    //create Account
    @POST("create_account")
    suspend fun createAccount(
        @Body createAccountRequestModel: CreateAccountRequestModel
    ): Response<LoginResponseModel>


 //confirm booking
    @POST("confirm_booking")
    suspend fun confirmBooking(
        @Body confirmBookingRequestModel: ConfirmBookingRequestModel
    ): Response<ConfirmBookingResponseModel>


    //get slot
    @GET("get_slots")
    suspend fun getSlots(@Query("date") date: String): Response<SlotResponseModel>


    //get booking history
    @GET("get_bookings")
    suspend fun getBookingHistorys(@Query("user_id") user_id: String): Response<BookingHistoryResponseModel>

    //get desk availbility
    @GET("get_availability")
    suspend fun getDesks(   @Query("date")  date : String,
                            @Query("slot_id")  slot_id : String,
                            @Query("type")  type : String,): Response<DeskResponseModel>
}