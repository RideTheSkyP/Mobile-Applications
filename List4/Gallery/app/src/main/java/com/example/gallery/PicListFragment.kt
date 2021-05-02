package com.example.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class PicListFragment : ListFragment()
{
    var imageList = ArrayList<MyPicItem>()
    var currentItem = -1

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        loadImages()
        return inflater.inflate(R.layout.pic_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        imgInit()
        listAdapter = MyItemAdapter(view.context, imageList)
        listView.setOnItemClickListener { _, _, position, _ ->
            currentItem = position
            showDetails(position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("items", imageList)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?)
    {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
        {
            val temp = savedInstanceState.getParcelableArrayList<MyPicItem>("items")
            if (temp != null) {
                imageList = temp
                listAdapter = MyItemAdapter(requireActivity().baseContext, temp)
            }
        }
    }

    fun loadImages()
    {
        val file = File(context?.filesDir, "items.json")
        if (file.exists()) {
            val itemsJson = JSONArray(file.readText())
            for (i in 0 until itemsJson.length())
            {
                imageList.add(MyPicItem(itemsJson[i] as JSONObject))
            }
        }
        else
        {
            val string = context?.assets?.open("items.json")?.bufferedReader().use{
                it?.readText()
            }
            val itemsJson = JSONArray(string)
            for (i in 0 until itemsJson.length()) {
                imageList.add(MyPicItem(itemsJson[i] as JSONObject))
            }
        }
        println("Loaded")
    }

    fun showDetails(idx: Int) : Boolean
    {
        val myIntent = Intent(activity, EditionActivity::class.java)
        myIntent.putExtra("item", imageList[idx])
        startActivityForResult(myIntent, 123)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123)
        {
            val item: MyPicItem = data!!.getParcelableExtra("after")
            imageList[currentItem].desc = item.desc
            imageList[currentItem].rating = item.rating
            imageList = ArrayList(imageList.sortedByDescending { it.rating } )
            listAdapter = MyItemAdapter(requireView().context, imageList)
        }
    }

    fun imgInit()
    {
        imageList.addAll(listOf(
            MyPicItem(R.drawable.img, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img1, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img2, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img3, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img4, getString(R.string.firstDescription), 0f)
        ))
    }

    override fun onStop()
    {
        super.onStop()
        val jsonArray = JSONArray()
        imageList.forEach { jsonArray.put(it.toJson()) }
        File(context?.filesDir, "items.json").printWriter().use {
            it.println(jsonArray.toString())
        }
    }
}