package com.example.co_working.dialogbox

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.co_working.R
import kotlinx.android.synthetic.main.confirm_booking_dialog.*
import kotlinx.android.synthetic.main.confirm_booking_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*

class ConfirmBookingDialogFragment : DialogFragment() {

    private var confirmBookingListener: ConfirmBookingListener? = null


    companion object {

        fun newInstance(date: String,slotId:String,workspace_id:String,type:String,selectedTime:String): ConfirmBookingDialogFragment {
            val fragment = ConfirmBookingDialogFragment()
            val args = Bundle()
            args.putString("date", date)
            args.putString("slotId", slotId)
            args.putString("workspace_id", workspace_id)
            args.putString("type", type)
            args.putString("selectedTime", selectedTime)
            fragment.arguments = args
            return fragment
        }
    }

    private var date: String? = null
    private var slotId: String? = null
    private var workspace_id: String? = null
    private var type: String? = null
    private var selectedTime: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.confirm_booking_dialog, null)

            arguments?.let {
                date = it.getString("date");
                slotId = it.getString("slotId");
                workspace_id = it.getString("workspace_id");
                type = it.getString("type");
                selectedTime = it.getString("selectedTime");

                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val outputFormat = SimpleDateFormat("E dd MMM", Locale.US)

                val formated: Date = inputFormat.parse(date.toString()) as Date
                val formattedDate: String = outputFormat.format(formated)

                dialogView.confirm_desk_no.text = workspace_id;
                dialogView.slot_date.text = "$formattedDate , $selectedTime"

                Log.d("=====bokingdetals",date.toString()+" "+slotId+" "+workspace_id+" "+type)

//                tv_slot_date.text = date
            }
//            val confirmButton :Button = dialogView.confirmButton

            dialogView.confirmButton.setOnClickListener{
                dismiss();
                confirmBookingListener?.onBookingConfirmed()

            }

            dialogView.cancle_dialog.setOnClickListener{
                dismiss();
            }


            builder.setView(dialogView)


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setConfirmBookingListener(listener: ConfirmBookingListener) {
        confirmBookingListener = listener
    }


    interface ConfirmBookingListener {
        fun onBookingConfirmed()
    }
}
