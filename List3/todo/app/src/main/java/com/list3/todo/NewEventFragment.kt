package com.list3.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_new_event.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

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
