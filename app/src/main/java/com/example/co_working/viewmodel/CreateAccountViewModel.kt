package com.example.co_working.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.co_working.app.LoginApp
import com.example.co_working.model.request.CreateAccountRequestModel
import com.example.co_working.model.request.LoginRequestModel
import com.example.co_working.model.response.LoginResponseModel
import com.example.co_working.repository.LoginRepository
import com.example.co_working.utils.Event
import com.example.co_working.utils.Resource
import com.example.co_working.utils.hasInternetConnection
import com.example.co_working.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    application: Application,
    private val repository: LoginRepository
) :
    AndroidViewModel(application) {
    private val _loginData = MutableLiveData<Event<Resource<LoginResponseModel>>>()

    val loginData: LiveData<Event<Resource<LoginResponseModel>>> = _loginData

    fun createAccountUser(dataModelLoginBody: CreateAccountRequestModel) = viewModelScope.launch {
        createAccount(dataModelLoginBody)
    }

    suspend fun createAccount(createAccountRequestModel: CreateAccountRequestModel) {
        _loginData.postValue(Event(Resource.Loading()))
        try {
            if (hasInternetConnection<LoginApp>()) {
                val response = repository.createAccount(createAccountRequestModel)
                Log.d("======response", response.toString())
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val successresponse: LoginResponseModel? = response.body()
                        Log.d("======successresponse", successresponse.toString())
                        toast(getApplication(), successresponse!!.message)
                        _loginData.postValue(Event(Resource.Success(response.body()!!)))
                    } else if (response.body()!!.status == 401) {

                        val errorresponse: LoginResponseModel? = response.body()
//                        toast(getApplication(), errorresponse!!.error)

                    } else if (response.body()!!.status == 412) {

                        val errorresponse: LoginResponseModel? = response.body()
//                        toast(getApplication(), errorresponse!!.error)
                    }

                } else {
                    _loginData.postValue(Event(Resource.Error(response.message())))
                }
            } else {
                _loginData.postValue(Event(Resource.Error("No Internet Connection")))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (e: HttpException) {
            when (e) {
                is IOException -> {
                    _loginData.postValue(Event(Resource.Error(e.message!!)))
                    toast(getApplication(), "Exception ${e.message}")
                }

            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _loginData.postValue(Event(Resource.Error(t.message!!)))
                    toast(getApplication(), t.message!!)
                }

            }

        }
    }
}