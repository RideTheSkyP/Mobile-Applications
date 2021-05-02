package com.example.gallery


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }

    override fun onStop()
    {
        super.onStop()
        val jsonArray = JSONArray()
//        imageList.forEach { jsonArray.put(it.toJson()) }
//        File(this.filesDir, "items.json").printWriter().use {
//            it.println(jsonArray.toString())
//        }
    }
}
