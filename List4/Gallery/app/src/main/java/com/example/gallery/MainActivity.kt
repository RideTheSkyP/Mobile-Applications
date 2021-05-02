package com.example.gallery


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import org.json.JSONArray
import java.io.File


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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    override fun onStop()
    {
        super.onStop()
    }
}
