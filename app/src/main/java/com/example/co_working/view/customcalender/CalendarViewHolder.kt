package com.example.co_working.view.customcalender

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R

class CalendarViewHolder(
    itemView: View,
    daysOfMonth: ArrayList<CalenderModel>
) :
    RecyclerView.ViewHolder(itemView) {
    val dayOfMonth: TextView
    val numMonth: TextView
    val dateMonth: TextView
    val fullDate: TextView

    init {
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        dateMonth = itemView.findViewById(R.id.celldate)
        numMonth = itemView.findViewById(R.id.cellMonth)
        fullDate = itemView.findViewById(R.id.fullDate)
//        val cellBg: ConstraintLayout = itemView.findViewById(R.id.cellBg);

    }


}