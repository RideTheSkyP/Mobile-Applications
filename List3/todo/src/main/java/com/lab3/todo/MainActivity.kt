package com.lab3.todo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lab3.todo.MyRecyclerViewAdapter.ItemClickListener
import com.lab3.todo.databinding.ActivityMainBinding
import com.lab3.todo.databinding.InfoPickerBinding


class MainActivity : AppCompatActivity(), ItemClickListener
{
    private lateinit var binding: ActivityMainBinding
    var adapter: MyRecyclerViewAdapter? = null
    val recycleList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recycleList.add("Item0")

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter(this, recycleList)
        adapter!!.setClickListener(this)
        adapter!!.setOnLongClickListener(this)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onItemClick(view: View?, position: Int)
    {
        Toast.makeText(
                this,
                "Click " + adapter!!.getItem(position) + " on row number " + position,
                Toast.LENGTH_SHORT
        ).show()
    }

    override fun onLongClick(view: View?, position: Int)
    {
        Toast.makeText(
                this,
                "Long click " + adapter!!.getItem(position) + " on row number " + position,
                Toast.LENGTH_SHORT
        ).show()
        recycleList.removeAt(position)
        adapter?.notifyItemRemoved(position)
    }

    fun onTaskAddButton(view: View)
    {
        val myIntent = Intent(this, InfoPicker::class.java)
        startActivity(myIntent)
//        setContentView(bindingInfo.root)
//        val length = adapter?.itemCount ?: 0
//        recycleList.add(length, "Item")
//        adapter?.notifyItemInserted(length)
//        recycleList.add("Item")
//        adapter?.notifyDataSetChanged()
    }
}

//@Entity(tableName="events")
//data class Events
//{
//    private
//}
