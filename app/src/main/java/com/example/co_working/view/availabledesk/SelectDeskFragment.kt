package com.example.co_working.view.availabledesk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.co_working.R
import com.example.co_working.databinding.FragmentSelectDeskBinding
import com.example.co_working.dialogbox.ConfirmBookingDialogFragment
import com.example.co_working.model.Availability
import com.example.co_working.model.request.ConfirmBookingRequestModel
import com.example.co_working.model.request.DeskAvailabilityRequestModel
import com.example.co_working.utils.Resource
import com.example.co_working.view.base.BaseFragment
import com.example.co_working.viewmodel.DeskViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_desk.*
import kotlinx.android.synthetic.main.fragment_select_slot.progressbar_getslot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SelectDeskFragment : BaseFragment<FragmentSelectDeskBinding, DeskViewModel>(),
    DeskAdapter.OnItemClickListener ,ConfirmBookingDialogFragment.ConfirmBookingListener {
    override val viewModel: DeskViewModel by viewModels()
    private lateinit var adapter: DeskAdapter

    // Global variables
    var slotId: String = ""
    var date: String = ""
    var type: String = ""
    var selectedTime: String = ""
    var workspaceId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = requireActivity() as AppCompatActivity
        mainActivity.supportActionBar?.title = "Available desks"

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        slotId = requireArguments().getString("slot_id").toString()
        date = requireArguments().getString("date").toString()
        type = requireArguments().getString("type").toString()
        selectedTime = requireArguments().getString("selected_time").toString()

            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val outputFormat = SimpleDateFormat("E dd MMM", Locale.US)

            val formated: Date = inputFormat.parse(date) as Date
            val formattedDate: String = outputFormat.format(formated)


            tv_selecteddate.text = "$formattedDate, $selectedTime"



        val deskAvailabilityRequestModel = DeskAvailabilityRequestModel(date, slotId, type);

        getDesks(deskAvailabilityRequestModel)

        button_book_desk.setOnClickListener {

            viewModel.isItemClicked.observe(viewLifecycleOwner) { workspace ->
                // Update the visibility of the button based on the item click state
                workspaceId = workspace.workspace_id.toString()
                val dialogFragment = ConfirmBookingDialogFragment.newInstance(date,slotId,workspaceId,type,selectedTime)
                dialogFragment.setConfirmBookingListener(this) // Set the listener to the Fragment
                dialogFragment.show(parentFragmentManager, "ConfirmBookingDialog")
            }


        }

        // Assuming you have a reference to the model instance
        viewModel.isItemClicked.observe(viewLifecycleOwner) { isClicked ->
            // Update the visibility of the button based on the item click state
            if (isClicked.workspace_active) {
                button_book_desk.visibility = View.VISIBLE
            } else {
                button_book_desk.visibility = View.GONE
            }
        }
    }


    private fun getDesks(deskAvailabilityRequestModel: DeskAvailabilityRequestModel) {

        desk_Recycler.layoutManager = GridLayoutManager(context, 6)
        viewModel.getDesk(deskAvailabilityRequestModel)
        viewModel.slotData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->

                when (response) {

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->

                            adapter = loginResponse.availability?.let { DeskAdapter(it) }!!
                            desk_Recycler.adapter = adapter
                            adapter.setOnItemClickListener(this) // 'this' refers to the fragment implementing OnItemClickListener

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


    override fun onItemClick(item: Availability) {
        // Handle the click event
        // Here, you have access to the selected item object (item)
        Log.d("==========items_click", item.workspace_name.toString());
        viewModel.setItemClicked(item)
    }

    private fun showProgressBar() {
        progressbar_getslot.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar_getslot.visibility = View.GONE
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSelectDeskBinding.inflate(inflater, container, false)

    override fun onBookingConfirmed() {
//        TODO("Not yet implemented")
//        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
//        Log.d("========click","$slotId $date $type")
        val confirmBookingRequestModel = ConfirmBookingRequestModel(date,slotId, workspaceId,type);
        confirmBooking(confirmBookingRequestModel)


    }

    private  fun confirmBooking(confirmBookingRequestModel: ConfirmBookingRequestModel) {

        viewModel.confirmBookingFun(confirmBookingRequestModel)
        viewModel.slotConfirm.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->

                Log.d("============confirmBooking3", response.data.toString())
                when (response) {

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            // Get the CoordinatorLayout from your layout file
                            // Get the CoordinatorLayout from your layout file
                            findNavController().navigate(R.id.action_selectDeskFragment_to_homeFragment);
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

}

