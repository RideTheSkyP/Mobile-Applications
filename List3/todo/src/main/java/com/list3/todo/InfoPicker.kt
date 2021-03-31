package com.list3.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.room.Room
import com.list3.todo.databinding.InfoPickerBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList


class InfoPicker : Activity()
{
    private lateinit var bindingInfo: InfoPickerBinding
    var imageArray: ArrayList<Icons> = ArrayList()
    var adapter: ImageTextAdapter? = null
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var inputDate: String = LocalDateTime.now().format(formatter)
    var inputTitle = ""
    var inputDesc = ""
    var selectedIcon = 0
    var inputPriority = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        bindingInfo = InfoPickerBinding.inflate(layoutInflater)
        setContentView(bindingInfo.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        imageArray.add(Icons(R.drawable.work, "Work"))
        imageArray.add(Icons(R.drawable.home, "Home"))
        imageArray.add(Icons(R.drawable.weekend, "Weekend"))
        imageArray.add(Icons(R.drawable.park, "Outdoor activities"))

        adapter = ImageTextAdapter(this, R.layout.icon_row, imageArray)
        val dataList: ListView = findViewById<View>(R.id.iconsList) as ListView
        dataList.adapter = adapter

        dataList.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, imageArray[position].name, Toast.LENGTH_LONG).show()
        }
    }

    fun addInfo(view: View)
    {
        inputDate = bindingInfo.dateInput.text.toString()
        inputTitle = bindingInfo.eventTitleInput.text.toString()
        inputDesc = bindingInfo.eventInput.text.toString()
        val strToParse = bindingInfo.priorityInput.text.toString()
        inputPriority = if (strToParse.isNotEmpty()) Integer.parseInt(strToParse) else 0
        println("add: $inputDate $inputTitle $inputDesc $selectedIcon $inputPriority")
        storeToDatabase(inputPriority, selectedIcon, inputDate, inputTitle, inputDesc)
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }

    fun storeToDatabase(inputPriority: Int, selectedIcon: Int, inputDate: String, inputTitle: String, inputDesc: String)
    {
        val db = Room.databaseBuilder(
                applicationContext,
                EventDatabase::class.java, "event_database"
        ).build()
        val eventDB = db.eventDao()
        val inp = EventDBCreator(priority=inputPriority, icon=selectedIcon, date=inputDate, title=inputTitle, desc=inputDesc)
        eventDB.insert(inp)
    }
}