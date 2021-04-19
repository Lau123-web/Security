package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_layout.view.*

class DataAdapter(var list:ArrayList<DatabaseModel>):RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        var roomID = itemView.tv_roomID
        var cname = itemView.tv_name
        var pNo = itemView.tv_phoneN
        var numG = itemView.tv_numG
        var custR = itemView.tv_custR
        var bDay = itemView.tv_bDay
        var tCost = itemView.tv_tCost
        var startD = itemView.tv_startD
        var endD = itemView.tv_endD

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_layout,parent,false ))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.roomID.text = list[position].roomID
        holder.cname.text = list[position].name
        holder.pNo.text = list[position].pNo.toString()
        holder.numG.text = list[position].numG.toString()
        holder.custR.text = list[position].custR
        holder.bDay.text = list[position].bDay
        holder.tCost.text = list[position].tCost
        holder.startD.text = list[position].startD
        holder.endD.text = list[position].endD
    }

    override fun getItemCount(): Int {
        return list.size
    }
}