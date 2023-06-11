package com.example.co_working.view.selectslot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.databinding.FragmentSelectSlotBinding
import com.example.co_working.model.Slot
import com.example.co_working.utils.Resource
import com.example.co_working.view.base.BaseFragment
import com.example.co_working.view.customcalender.CalendarAdapter
import com.example.co_working.view.customcalender.CalenderModel
import com.example.co_working.viewmodel.SelectSlotViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_slot.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.time.YearMonth


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SelectSlotFragment : BaseFragment<FragmentSelectSlotBinding, SelectSlotViewModel>(),
    SlotAdapter.OnItemClickListener, CalendarAdapter.OnItemListener {
    override val viewModel: SelectSlotViewModel by viewModels()
    private lateinit var adapter: SlotAdapter
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var selectedDate: LocalDate? = null
    private var selectedDateValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = requireActivity() as AppCompatActivity
        mainActivity.supportActionBar?.title = "Select Date & Slot"


        calendarRecyclerView = calender_rv;
        selectedDate = LocalDate.now();

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setMonthView();
        getSlot()
        button_next_slot.setOnClickListener {

            findNavController().navigate(R.id.action_selectSlotFragment_to_selectDeskFragment,
                Bundle().apply {
                    // Assuming you have a reference to the model instance
                    viewModel.isItemClicked.observe(viewLifecycleOwner) { slot ->
                        // Update the visibility of the button based on the item click state
                        putString("slot_id", slot.slot_id.toString())
                        putString("date", selectedDateValue)
                        putString("selected_time", slot.slot_name)
                        putString("type", requireArguments().getString("type").toString())
                    }

                })
        }
        viewModel.isItemClicked.observe(viewLifecycleOwner) { isClicked ->
            // Update the visibility of the button based on the item click state
            if (isClicked.slot_active) {
                button_next_slot.visibility = View.VISIBLE
            } else {
                button_next_slot.visibility = View.GONE
            }
        }
    }


    private fun getSlot() {


        slot_Recycler.layoutManager = GridLayoutManager(context, 2)
        selectedDateValue?.let { viewModel.getSlot(it) }
        viewModel.slotData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->

                when (response) {

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->

                            adapter = loginResponse.slots?.let { SlotAdapter(it) }!!
                            slot_Recycler.adapter = adapter
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

    //
    override fun onItemClickSlot(item: Slot) {
        // Handle the click event
        // Here, you have access to the selected item object (item)
        Log.d("==========items_click", item.slot_name.toString());
        viewModel.setItemClicked(item)
    }

    private fun showProgressBar() {
        progressbar_getslot.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar_getslot.visibility = View.GONE
    }


    private fun setMonthView() {
        val daysInMonth = daysInMonthArray(selectedDate!!)
        val calendarAdapter = CalendarAdapter(daysInMonth, this@SelectSlotFragment)
//        calender_rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        calender_rv?.layoutManager = LinearLayoutManager(context,GridLayoutManager.HORIZONTAL, 7)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        calender_rv?.layoutManager = layoutManager
        calender_rv?.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<CalenderModel> {
        val daysInMonthArray: ArrayList<CalenderModel> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate = date.withDayOfMonth(1)
        val dayOfWeek: Int = firstOfMonth.dayOfWeek.value
        val currentMonth: String = date.month.toString()
        val currentYear: String = date.year.toString()

        // Create an array of weekday abbreviations
        val weekdays = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        // Calculate the offset for weekdays
        val weekdayOffset = (dayOfWeek - 1 + 7) % 7

        for (i in 1..yearMonth.lengthOfMonth()) {
            val calenderModel = CalenderModel()

            val dayOfMonth = i
            calenderModel.day = dayOfMonth.toString()
            calenderModel.month = currentMonth
//                calenderModel.monthNumber = date.monthValue // Get the month number
            val monNum = String.format("%02d", date.monthValue) // Get the month number with leading zeros

            calenderModel.date = weekdays[(i - 1) % 7]  // Get the weekday abbreviation
            calenderModel.fullday = "$currentYear-$monNum-${String.format("%02d", dayOfMonth)}"
//            }
            daysInMonthArray.add(calenderModel)
        }

        selectedDateValue = daysInMonthArray[0].fullday.toString();
        return daysInMonthArray
    }
//
//    private fun monthYearFromDate(date: LocalDate): String {
//        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
//        return date.format(formatter)
//    }

//    fun previousMonthAction(view: View?) {
//        selectedDate = selectedDate.minusMonths(1)
//        setMonthView()
//    }
//
//    fun nextMonthAction(view: View?) {
//        selectedDate = selectedDate.plusMonths(1)
//        setMonthView()
//    }

//    fun onItemClick(position: Int, dayText: String) {
//        if (dayText != "") {
//            val message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate)
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//        }
//    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSelectSlotBinding.inflate(inflater, container, false)

    override fun onItemClick(dayText: String?) {
//        TODO("Not yet implemented")
//        Toast.makeText(context, "date " + dayText, Toast.LENGTH_SHORT).show()
        if (dayText != null) {
            selectedDateValue = dayText
        };

        getSlot()
    }

}