package com.example.co_working.view.availabledesk

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Availability
import com.example.co_working.model.Slot

class DeskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.dateTextView_desk)

    fun bind(item: Availability) {
        titleTextView.text = item.workspace_name.toString()
//        titleTextView.text = item.workspace_name.toString()

    }


}
