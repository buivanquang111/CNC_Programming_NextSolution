package com.example.nextsolution_ex1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview.view.*

class CNCAdapter(private val context: Context?, private var list: ArrayList<CNC>): RecyclerView.Adapter<CNCAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var pos: Int = 0
        lateinit var cnc: CNC

        fun setData(cnc: CNC,position: Int){
            itemView.textViewTitle.text = cnc.title
            this.cnc = cnc
            this.pos = position
        }
        fun setListeners(){
            itemView.setOnClickListener {
                val myCommunicator = context as MyCommunicator?
                myCommunicator?.displayDetails(cnc.index,cnc.title,cnc.url,list)
                //Toast.makeText(context,"b vua click ${cnc.url}",Toast.LENGTH_SHORT).show()
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
        holder.setData(cnc,position)
        holder.setListeners()
    }


}