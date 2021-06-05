package com.example.nextsolution_ex1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.nextsolution_ex1.database.CNCDataBase
import com.example.nextsolution_ex1.database.CNCObject
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import kotlinx.android.synthetic.main.item_yeuthich.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class YeuThichAdapter internal constructor(context: Context, val cncDB: CNCDataBase) :
    RecyclerView.Adapter<YeuThichAdapter.ViewHolder>() {
    private var list = emptyList<CNCObject>()
    private lateinit var listener: OnItemClickLister
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    internal fun setCNC(cnc: List<CNCObject>, listener: OnItemClickLister) {
        this.list = cnc
        this.listener = listener
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var txt: TextView
        var itemRecyclerView: ConstraintLayout
        var imageViewDelete: ImageView

        init {
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete)
            txt = itemView.findViewById(R.id.textViewTitleYeuThich)
            itemRecyclerView = itemView.findViewById(R.id.itemRecyclerViewYeuThich)
            itemRecyclerView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YeuThichAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_yeuthich, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: YeuThichAdapter.ViewHolder, position: Int) {
        var cnc: CNCObject = list[position]
        holder.txt.text = cnc.title
        holder.imageViewDelete.setOnClickListener {
            uiScope.launch {
                cncDB?.cncDao().delete(cnc)
                list = cncDB?.cncDao()?.getListCNC()
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickLister {
        fun onItemClick(position: Int)
    }
}