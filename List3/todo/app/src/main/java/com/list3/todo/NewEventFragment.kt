package com.list3.todo

import android.R.attr.maxDate
import android.R.attr.minDate
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_new_event.*
import java.util.*
import kotlin.collections.ArrayList


class NewEventFragment : Fragment()
{
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mViewModel: NewEventViewModel
    var imageArray: ArrayList<Icons> = ArrayList()
    var adapter: ImageTextAdapter? = null
    var inputTitle = ""
    var inputDesc = ""
    var selectedIcon = 0
    var inputPriority = 100
    var inputDate = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(NewEventViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_new_event, container, false)
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
        {
            listener = context
        }
        else
        {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

//    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
//        isFocusableInTouchMode = false
//        isClickable = true
//        isFocusable = false
//
//        val myCalendar = Calendar.getInstance()
//        val datePickerOnDataSetListener =
//                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//                    myCalendar.set(Calendar.YEAR, year)
//                    myCalendar.set(Calendar.MONTH, monthOfYear)
//                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                    val sdf = SimpleDateFormat(format, Locale.UK)
//                    setText(sdf.format(myCalendar.time))
//                }
//
//        setOnClickListener {
//            DatePickerDialog(
//                    context, datePickerOnDataSetListener, myCalendar
//                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                    myCalendar.get(Calendar.DAY_OF_MONTH)
//            ).run {
//                maxDate?.time?.also { datePicker.maxDate = it }
//                show()
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val editTextDatePicker = TextViewDatePicker(context, dateInput)
        println(editTextDatePicker)

        imageArray.add(Icons(R.drawable.work, "Work"))
        imageArray.add(Icons(R.drawable.home, "Home"))
        imageArray.add(Icons(R.drawable.weekend, "Weekend"))
        imageArray.add(Icons(R.drawable.park, "Outdoor activities"))

        adapter = ImageTextAdapter(activity!!, R.layout.icon_row, imageArray)
        iconsList.adapter = adapter

        iconsList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(activity, imageArray[position].name, Toast.LENGTH_LONG).show()
            selectedIcon = position
        }

        confirmationButton.setOnClickListener {
            inputTitle = eventTitleInput.text.toString().trim()
            inputDesc = eventInput.text.toString().trim()
            inputDate = dateInput.text.toString().trim()
            inputPriority = if (priorityInput.text.toString().isNotEmpty()) Integer.parseInt(priorityInput.text.toString()) else 0

            if (inputTitle.isEmpty())
            {
                Toast.makeText(activity, "Title required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputTitle.length > 30)
            {
                Toast.makeText(activity, "Title too long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mViewModel.storeEvent(inputTitle, inputDesc, inputPriority, selectedIcon, inputDate)
            Toast.makeText(activity, "$inputTitle entered", Toast.LENGTH_SHORT).show()
            listener?.goToEventListFragment()
        }
    }

    override fun onDetach()
    {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener
    {
        fun goToEventListFragment()
    }

    companion object
    {
        @JvmStatic
        fun newInstance() = NewEventFragment()
    }
}
