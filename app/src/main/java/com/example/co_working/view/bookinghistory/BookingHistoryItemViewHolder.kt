package com.example.co_working.view.bookinghistory

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Slot
import com.example.co_working.model.response.BookingHistoryItem

class BookingHistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.dateTextView_booking)

    fun bind(item: BookingHistoryItem) {
        titleTextView.text = item.workspace_name.toString()

    }


}
