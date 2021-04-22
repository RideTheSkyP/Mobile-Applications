package com.list3.todo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class TextViewDatePicker(context: Context?, private val mView: TextView) : View.OnClickListener, OnDateSetListener
{
    var datePickerDialog: DatePickerDialog? = null
        private set
    private val mContext: Context?

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int)
    {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = monthOfYear
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val date = calendar.time
        val formatter = SimpleDateFormat(DATE_SERVER_PATTERN)
        mView.text = formatter.format(date)
        println(date)
    }

    override fun onClick(v: View?)
    {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        datePickerDialog = DatePickerDialog(mContext!!, this, calendar[Calendar.YEAR],
                calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
        datePickerDialog!!.show()
    }

    companion object
    {
        const val DATE_SERVER_PATTERN = "yyyy-MM-dd"
    }

    init
    {
        mView.setOnClickListener(this)
        mView.isFocusable = false
        mContext = context
    }
}