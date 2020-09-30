package com.crexative.carshop.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Helpers {

    /**
     * Statics Method to manipulated  Data
     * */
    companion object {
        fun dateToCalendar(date: String?): Calendar? {
            val fmtOut = SimpleDateFormat("yyyy-MM-dd")
            val calendar: Calendar = Calendar.getInstance()
            try {
                val mDate: Date = fmtOut.parse(date)
                calendar.time = mDate
            } catch (e: ParseException) {
                e.message
            }
            return calendar
        }

        fun readebleDate(dateString: String?): String? {
            var fmt = SimpleDateFormat("yyyy-MM-dd")
            var fecha = dateString
            try {
                val date = fmt.parse(dateString)
                fmt = SimpleDateFormat("E d MMM")
                fecha = fmt.format(date)
            } catch (e: ParseException) {
                e.message
                fecha = ""
            }
            return fecha
        }
    }

}