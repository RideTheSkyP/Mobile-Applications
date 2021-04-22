package com.list3.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.list3.todo.database.Event
import kotlinx.android.synthetic.main.fragment_movie_list.*


class EventRecyclerAdapter(private val provider: NewEventViewModel, private val myDataset: List<Event>) : RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder>()
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

    fun getItem(id: Int): Event
    {
        return myDataset[id]
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int)
    {
        println("Pos: ${myDataset[position].title} ${myDataset[position]}, $position")
        holder.title.text = myDataset[position].title
        holder.data.text = myDataset[position].desc
        holder.date.text = myDataset[position].date

        var iconImage: Int = R.drawable.work
        when (myDataset[position].icon) {
            1 -> iconImage = R.drawable.home
            2 -> iconImage = R.drawable.weekend
            3 -> iconImage = R.drawable.park
        }

        holder.itemView.setOnClickListener {
//            Toast.makeText(, "Recycle Click $position", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener {
            println(myDataset[position].title)
            if (position <= itemCount)
            {
                provider.deleteEvent(getItem(position))
                notifyItemRemoved(position)
                provider.retrieveMovies()
            }
            true
        }
        holder.icon.setImageResource(iconImage)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var title: TextView = itemView.findViewById(R.id.recyclerViewTitle)
        var data: TextView = itemView.findViewById(R.id.recyclerViewData)
        var icon: ImageView = itemView.findViewById(R.id.iconIV)
        var date: TextView = itemView.findViewById(R.id.recyclerViewDate)
//        var priority: TextView = itemView.findViewById(R.id.recyclerViewData)
    }
}
