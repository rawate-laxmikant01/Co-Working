package com.example.co_working.view.selectslot

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Slot

class SlotItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.dateTextView)

    fun bind(item: Slot) {
        titleTextView.text = item.slot_name.toString()

    }


}
