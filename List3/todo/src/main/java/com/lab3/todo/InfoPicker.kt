package com.lab3.todo

import android.app.Activity
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import com.lab3.todo.databinding.InfoPickerBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
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
        println("add: $inputDate $inputTitle $inputDesc $selectedIcon")
    }
}