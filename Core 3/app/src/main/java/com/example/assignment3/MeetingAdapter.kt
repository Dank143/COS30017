package com.example.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MeetingAdapter(private var meetings: List<Meeting>) : RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>() {

    private var filteredMeetings: MutableList<Meeting> = meetings.toMutableList()

    inner class MeetingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clubName: TextView = view.findViewById(R.id.clubName)
        val location: TextView = view.findViewById(R.id.location)
        val type:TextView = view.findViewById(R.id.type)
        val dateTime:TextView = view.findViewById(R.id.dateTime)
        val icon: ImageView = view.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meeting = filteredMeetings[position]
        holder.clubName.text = meeting.clubName
        holder.location.text = meeting.location
        holder.type.text = meeting.type
        holder.dateTime.text = meeting.dateTime

        if (meeting.location == "Online") {
            holder.icon.setImageResource(R.drawable.online)
        } else {
            holder.icon.setImageResource(R.drawable.in_person)
        }
    }

    override fun getItemCount() = filteredMeetings.size

    private fun sortMeetings() {
        filteredMeetings = filteredMeetings.sortedWith(compareBy { it.type }).toMutableList()
        notifyDataSetChanged()
    }

    fun filter(type: String) {
        filteredMeetings = when (type) {
            "Online" -> { ArrayList(meetings.filter { it.location == "Online" }) }
            "In-person" -> { ArrayList(meetings.filter { it.location != "Online" }) }
            "All" -> { ArrayList(meetings) }
            else -> { ArrayList(meetings) }
        }
        sortMeetings()
    }
}

