package com.list3.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_new_event.*

class NewEventFragment : Fragment()
{
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mViewModel: NewEventViewModel

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

        button.setOnClickListener {
            val input = editText.text.toString().trim()

            if (input.isEmpty())
            {
                Toast.makeText(activity, "Title required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (input.length > 30)
            {
                Toast.makeText(activity, "Title too long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mViewModel.storeEvent(input)
            Toast.makeText(activity, "$input entered", Toast.LENGTH_SHORT).show()
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
