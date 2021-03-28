package com.lab3.todo

import android.content.ClipData.Item
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lab3.todo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val itemList = ArrayList<Item>()
//        var recyclerAdapter = binding.recyclerView.layoutManager
//        val itemArrayAdapter = ItemArrayAdapter(itemList)
//        recyclerAdapter = LinearLayoutManager(this)
//        recyclerAdapter.addView(TextView)
//        binding.recyclerView.itemAnimator = DefaultItemAnimator()
//        binding.recyclerView.adapter = itemList
    }

    fun onTaskAddButton(view: View)
    {
        val valueTV = TextView(this)
        valueTV.text = "hallo hallo"
        valueTV.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        binding.recyclerView.layoutManager.addView(valueTV)
    }
}