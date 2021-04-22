package com.list3.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyRecyclerViewAdapter internal constructor(context: Context?, data: List<String>) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>()
{
    private val mData: List<String> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view: View = mInflater.inflate(R.layout.recyclerview_row, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val data = mData[position]
        holder.myTextView.text = data
    }

    // total number of rows
    override fun getItemCount(): Int
    {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener
    {
        var myTextView: TextView = itemView.findViewById(R.id.recyclerViewTitle)

        override fun onClick(view: View?)
        {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        override fun onLongClick(view: View?): Boolean
        {
            if (mClickListener != null) mClickListener!!.onLongClick(view, adapterPosition)
            return true
        }

        init
        {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }

    fun getItem(id: Int): String
    {
        return mData[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?)
    {
        mClickListener = itemClickListener
    }

    fun setOnLongClickListener(itemClickListener: ItemClickListener?)
    {
        mClickListener = itemClickListener
    }

    interface ItemClickListener
    {
        fun onItemClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
    }
}