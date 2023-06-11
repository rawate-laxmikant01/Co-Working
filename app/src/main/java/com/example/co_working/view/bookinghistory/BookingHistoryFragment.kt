package com.example.co_working.view.bookinghistory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.co_working.R
import com.example.co_working.databinding.FragmentBookingHistoryBinding
import com.example.co_working.databinding.FragmentSelectSlotBinding
import com.example.co_working.model.Slot
import com.example.co_working.utils.Resource
import com.example.co_working.view.base.BaseFragment
import com.example.co_working.view.selectslot.SlotAdapter
import com.example.co_working.viewmodel.BookingHistoryViewModel
import com.example.co_working.viewmodel.SelectSlotViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_booking_history.*
import kotlinx.android.synthetic.main.fragment_select_slot.*
import kotlinx.android.synthetic.main.fragment_select_slot.progressbar_getslot
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class  BookingHistoryFragment: BaseFragment<FragmentBookingHistoryBinding, BookingHistoryViewModel>(){
    override val viewModel: BookingHistoryViewModel by viewModels()
    private lateinit var adapter: BookingHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = requireActivity() as AppCompatActivity
        mainActivity.supportActionBar?.title = "Booking history"


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBookingHistory()

    }



    private fun getBookingHistory() {


        viewModel.getBookingHistory("1")
        viewModel.bookingHistoryData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->

                when (response) {

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->

                            adapter = loginResponse.bookings?.let { BookingHistoryAdapter(it) }!!
                            booking_history_Recycler.adapter = adapter


                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { toast(it) }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        })
    }


//    override fun onItemClick(item: Slot) {
//        // Handle the click event
//        // Here, you have access to the selected item object (item)
////        Log.d("==========items_click", item.slot_name.toString());
////        viewModel.setItemClicked(item)
//    }

    private fun showProgressBar() {
        progressbar_getslot.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar_getslot.visibility = View.GONE
    }



    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBookingHistoryBinding.inflate(inflater, container, false)
}