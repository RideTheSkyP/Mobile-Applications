package com.list3.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.list3.todo.data.Event

class EventRecyclerAdapter(private val myDataset: List<Event>) : RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return EventViewHolder(v)
    }

    override fun getItemCount(): Int
    {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int)
    {
        println("Pos: $position")
        holder.title.text = myDataset[position].title
//        holder.priority.text = myDataset[position].title
//        holder.icon.text = myDataset[position].title
//        holder.desc.text = myDataset[position].title
//        holder.title.text = myDataset[position].title
//        holder.title.text = myDataset[position].title
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var title: TextView = itemView.findViewById(R.id.recyclerViewTitle)
    }
}



