package com.example.co_working.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.co_working.app.LoginApp
import com.example.co_working.model.Slot
import com.example.co_working.model.request.LoginRequestModel
import com.example.co_working.model.response.LoginResponseModel
import com.example.co_working.model.response.SlotResponseModel
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
class SelectSlotViewModel @Inject constructor(
    application: Application,
    private val repository: LoginRepository
) :
    AndroidViewModel(application) {
    private val _slotData = MutableLiveData<Event<Resource<SlotResponseModel>>>()

    val slotData: LiveData<Event<Resource<SlotResponseModel>>> = _slotData

    private val _isItemClicked = MutableLiveData<Slot>()
    val isItemClicked: LiveData<Slot>
        get() = _isItemClicked

    // Function to update the state of the item click
    fun setItemClicked(isClicked: Slot) {
        _isItemClicked.value = isClicked
    }

    fun getSlot(date: String) = viewModelScope.launch {
        getSlots(date)
    }

    suspend fun getSlots(date: String) {
        _slotData.postValue(Event(Resource.Loading()))
        try {
            if (hasInternetConnection<LoginApp>()) {
                val response = repository.getSlots(date)
                Log.d("======getSlot", response.toString())
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val successresponse: SlotResponseModel? = response.body()
                        Log.d("======successresponse", successresponse.toString())
//                        toast(getApplication(), successresponse!!.message)
                        _slotData.postValue(Event(Resource.Success(response.body()!!)))
                    } else if (response.code() == 401) {

                        val errorresponse: SlotResponseModel? = response.body()
//                        toast(getApplication(), errorresponse!!.error)

                    } else if (response.code() == 412) {

                        val errorresponse: SlotResponseModel? = response.body()
//                        toast(getApplication(), errorresponse!!.error)
                    }

                } else {
                    _slotData.postValue(Event(Resource.Error(response.message())))
                }
            } else {
                _slotData.postValue(Event(Resource.Error("No Internet Connection")))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (e: HttpException) {
            when (e) {
                is IOException -> {
                    _slotData.postValue(Event(Resource.Error(e.message!!)))
                    toast(getApplication(), "Exception ${e.message}")
                }

            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _slotData.postValue(Event(Resource.Error(t.message!!)))
                    toast(getApplication(), t.message!!)
                }

            }

        }
    }
}