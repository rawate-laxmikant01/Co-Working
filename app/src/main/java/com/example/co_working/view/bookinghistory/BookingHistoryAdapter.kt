package com.example.co_working.view.bookinghistory


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Slot
import com.example.co_working.model.response.BookingHistoryItem

class BookingHistoryAdapter(private val items: List<BookingHistoryItem>) :
    RecyclerView.Adapter<BookingHistoryItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return BookingHistoryItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookingHistoryItemViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val item = items[position]
        holder.bind(item)


    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: Slot)
    }
}
