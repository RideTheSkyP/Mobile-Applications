package com.list3.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.list3.todo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.ItemClickListener
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
        val db = Room.databaseBuilder(
            applicationContext,
            EventDatabase::class.java, "event_database"
        ).build()
        val eventDB = db.eventDao()
        val inf = eventDB.getAll()
        inf.observe(this, Observer {  })
//        var mSections: LiveData<List<EventDBCreator>>


        println(inf.value)
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
