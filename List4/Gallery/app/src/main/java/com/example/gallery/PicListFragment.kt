package com.example.gallery

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class PicListFragment : ListFragment()
{
    var pics = ArrayList<MyPicItem>()
    var currentItm = -1

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.pic_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        picsInit()
        listAdapter = MyItemAdapter(view.context, pics)
        listView.setOnItemClickListener { _, _, position, _ ->
            currentItm = position
            showDetails(position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("items", pics)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?)
    {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null)
        {
            val temp = savedInstanceState.getParcelableArrayList<MyPicItem>("items")
            if (temp != null) {
                pics = temp
                listAdapter = MyItemAdapter(activity!!.baseContext, temp)
            }
        }
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long)
    {
        super.onListItemClick(l, v, position, id)
    }

    fun showDetails(idx: Int) : Boolean
    {
        val myIntent = Intent(activity, ScndActivity::class.java)
        myIntent.putExtra("item", pics[idx])
        startActivityForResult(myIntent, 123)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123)
        {
            val itm: MyPicItem = data!!.getParcelableExtra("after")
            pics[currentItm].desc = itm.desc
            pics[currentItm].rating = itm.rating
            pics = ArrayList(pics.sortedByDescending { it.rating } )
            listAdapter = MyItemAdapter(view!!.context, pics)
        }
    }

    fun picsInit()
    {
        pics.addAll(listOf(
            MyPicItem(R.drawable.img, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img1, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img2, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img3, getString(R.string.firstDescription), 0f),
            MyPicItem(R.drawable.img4, getString(R.string.firstDescription), 0f)
        ))
    }
}