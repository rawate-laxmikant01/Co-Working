package com.example.co_working.view.availabledesk


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Availability
import com.example.co_working.model.Slot
import com.example.co_working.view.selectslot.SlotItemViewHolder

class DeskAdapter(private val items: List<Availability>) :
    RecyclerView.Adapter<DeskItemViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeskItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_desk, parent, false)
        return DeskItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeskItemViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]
        holder.bind(item)

        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.primary_color))
        } else {
//            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(android.R.color.transparent))
            if (item.workspace_active) {
                holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.active_slot))
            } else {
                holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.inactive_slot))
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)

            // Update selected item position and trigger item change
            val previousSelectedItemPosition = selectedItemPosition
            selectedItemPosition = position
            notifyItemChanged(previousSelectedItemPosition)
            notifyItemChanged(selectedItemPosition)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: Availability)
    }
}
