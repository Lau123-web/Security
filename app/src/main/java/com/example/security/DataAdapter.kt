package com.example.security

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.security.DatabaseModel
import com.example.security.R
import kotlinx.android.synthetic.main.housekeep.view.*

class DataAdapter(var list: ArrayList<Room>):RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        var roomID = itemView.tv_view_roomID
        var checkin = itemView.tv_checkIn
        var expcheckout = itemView.tv_checkout
        var status = itemView.tv_status


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.housekeep,parent,false ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.checkin.text = list[position].checkin
        holder.expcheckout.text = list[position].expcheckout
        holder.roomID.text = list[position].roomID.toString()
        holder.status.text = list[position].status
    }

    override fun getItemCount(): Int {
        return list.size
    }
}