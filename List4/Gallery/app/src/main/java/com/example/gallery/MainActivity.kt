package com.example.gallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
        {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            val fragment = PicListFragment()
            transaction.add(R.id.mainLayout, fragment)
            transaction.commit()
        }
    }
}
