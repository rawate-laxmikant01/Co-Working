package com.example.co_working.view.customcalender

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Slot
import com.example.co_working.view.customcalender.CalendarAdapter.OnItemListener
import com.example.co_working.view.selectslot.SelectSlotFragment
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(
    private val daysOfMonth: ArrayList<CalenderModel>,
    onItemListener: SelectSlotFragment,
) :
    RecyclerView.Adapter<CalendarViewHolder>() {
    private val onItemListener: OnItemListener
    private var selectedItemPosition: Int = 0



    init {
        this.onItemListener = onItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view,daysOfMonth)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.primary_color))
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.white))
        }
        holder.dayOfMonth.text = daysOfMonth[position].day
        holder.numMonth.text = daysOfMonth[position].month
        holder.fullDate.text = daysOfMonth[position].fullday

        val date = LocalDate.parse(daysOfMonth[position].fullday)
        val dayOfWeek = date.dayOfWeek
        val dayAbbreviation = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)


        holder.dateMonth.text = dayAbbreviation

        holder.itemView.setOnClickListener {

            onItemListener.onItemClick(daysOfMonth[position].fullday);
            // Update selected item position and trigger item change
            val previousSelectedItemPosition = selectedItemPosition
            selectedItemPosition = position
            notifyItemChanged(previousSelectedItemPosition)
            notifyItemChanged(selectedItemPosition)
        }

    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick( dayText: String?)
    }

}