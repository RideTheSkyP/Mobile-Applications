package com.example.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar

class MyItemAdapter(context: Context, var data: ArrayList<MyPicItem>) : ArrayAdapter<MyPicItem>(context, R.layout.my_pic_item, data)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var view = convertView
        if (view == null)
        {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.my_pic_item, parent, false)
        }
        view!!.findViewById<ImageView>(R.id.imgItm).setImageResource(data[position].imgId)
        view.findViewById<RatingBar>(R.id.ratingBar).rating = data[position].rating
        return view
    }
}