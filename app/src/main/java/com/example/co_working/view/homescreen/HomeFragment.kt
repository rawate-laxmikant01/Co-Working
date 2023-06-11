package com.example.co_working.view.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.co_working.R
import com.example.co_working.databinding.FragmentHomeBinding
import com.example.co_working.view.base.BaseFragment
import com.example.co_working.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, DashboardViewModel>() {
    override val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        book_work_card.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectSlotFragment,
                Bundle().apply {
                    putString("type", "1")
                })
        }

        meeting_room_card.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectSlotFragment,
                Bundle().apply {
                    putString("type", "2")
                })
        }

        button_booking_history.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_bookingHistoryFragment);
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentHomeBinding.inflate(inflater, container, false)
}