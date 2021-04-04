package com.list3.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.list3.todo.data.EventDatabase
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class EventListFragment : Fragment()
{
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mViewModel: NewEventViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(NewEventViewModel::class.java)
        mViewModel.retrieveMovies().observe(this,  Observer
        {
            Timber.i("received the movies ${it.size}")
            rvList.adapter = EventRecyclerAdapter(it)
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        btnAdd.setOnClickListener {
            val dao =  EventDatabase.getInstance(this.context!!)?.eventDao()
            GlobalScope.launch {
               dao?.getAll()
            }
            listener?.goToNewEventFragment()
        }
        rvList.layoutManager = LinearLayoutManager(activity)
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

    override fun onDetach()
    {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener
    {
        fun goToNewEventFragment()
    }

    companion object
    {
        @JvmStatic
        fun newInstance() = EventListFragment()
    }
}
