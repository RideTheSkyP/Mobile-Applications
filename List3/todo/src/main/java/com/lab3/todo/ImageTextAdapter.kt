package com.lab3.todo

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ImageTextAdapter(context: Context, layoutResourceId: Int, data: ArrayList<Icons>): ArrayAdapter<Icons?>(context, layoutResourceId, data as List<Icons?>)
{
    var data: ArrayList<Icons> = ArrayList<Icons>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var row = convertView
        val holder: ImageHolder?

        if (row == null)
        {
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(R.layout.icon_row, parent, false)
            holder = ImageHolder()
            holder.txtTitle = row.findViewById<View>(R.id.iconDescription) as TextView
            holder.imgIcon = row.findViewById<View>(R.id.iconImage) as ImageView
            row.tag = holder
        }
        else
        {
            holder = row.tag as ImageHolder
        }

        val myImage: Icons = data[position]
        holder.txtTitle?.text = myImage.name
        val outImage: Int = myImage.image
        holder.imgIcon!!.setImageResource(outImage)
        return row!!
    }

    internal class ImageHolder
    {
        var imgIcon: ImageView? = null
        var txtTitle: TextView? = null
    }

    init
    {
        this.data = data
    }
}