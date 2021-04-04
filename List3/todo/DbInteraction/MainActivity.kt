package com.list3.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity(), NewEventFragment.OnFragmentInteractionListener, EventListFragment.OnFragmentInteractionListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        if (savedInstanceState == null)
        {
            goToEventListFragment()
        }
    }

    override fun goToNewEventFragment()
    {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.flContent, NewEventFragment.newInstance())
        transaction.commit()
    }

    override fun goToEventListFragment()
    {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.flContent, EventListFragment.newInstance())
        transaction.commit()
    }
}
