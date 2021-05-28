package com.example.nextsolution_ex1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CNCAdapter(private var list: ArrayList<CNC>, private val listener: OnItemClickLister): RecyclerView.Adapter<CNCAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var txt: TextView
        var itemRecyclerView: ConstraintLayout
        init {
            txt = itemView.findViewById(R.id.textViewTitle)
            itemRecyclerView = itemView.findViewById(R.id.itemRecyclerView)
            itemRecyclerView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = layoutPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cnc: CNC = list[position]
        holder.txt.text = cnc.title
    }
    interface OnItemClickLister{
        fun onItemClick(position: Int)
    }

}