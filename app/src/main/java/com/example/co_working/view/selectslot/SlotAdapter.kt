package com.example.co_working.view.selectslot


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.co_working.R
import com.example.co_working.model.Slot

class SlotAdapter(private val items: List<Slot>) :
    RecyclerView.Adapter<SlotItemViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION


    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slot, parent, false)
        return SlotItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SlotItemViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val item = items[position]
        holder.bind(item)

        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.primary_color))
        } else {
//            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(android.R.color.transparent))
            if (item.slot_active) {
                holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.active_slot))
            } else {
                holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.inactive_slot))
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClickSlot(item)

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
        fun onItemClickSlot(item: Slot)
    }
}
