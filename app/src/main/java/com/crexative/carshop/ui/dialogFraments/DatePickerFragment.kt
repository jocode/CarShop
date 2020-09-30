package com.crexative.carshop.ui.dialogFraments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.crexative.carshop.R
import com.crexative.carshop.utils.Helpers
import java.util.*


class DatePickerFragment : DialogFragment(), OnDateSetListener {
    private var mCallback: OnDateSelectedListener? = null
    private var mCalendar: Calendar? = null

    // Container Activity must implement this interface
    interface OnDateSelectedListener {
        fun onDateSelected(year: Int, month: Int, day: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        mCalendar = Helpers.dateToCalendar(arguments?.getString(DATE))
        val year: Int = mCalendar!!.get(Calendar.YEAR)
        val month: Int = mCalendar!!.get(Calendar.MONTH)
        val day: Int = mCalendar!!.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(context!!, R.style.datepicker, this, year, month, day)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mCallback = try {
            activity as OnDateSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " debe implementar OnDateSelectedListener"
            )
        }
    }

    override fun onDateSet(view: DatePicker, ano: Int, mes: Int, dia: Int) {
        mCallback!!.onDateSelected(ano, mes, dia)
    }

    companion object {
        const val DATE = "date_to_show"
    }
}