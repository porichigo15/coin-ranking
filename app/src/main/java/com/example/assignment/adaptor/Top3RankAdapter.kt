package com.example.assignment.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.adaptor.viewholder.Top3RankViewHolder
import com.example.assignment.model.Coin

class Top3RankAdapter(
    var list: ArrayList<Coin>,
    var onItemClickListener: OnItemClickListener,
    var context: Context
): RecyclerView.Adapter<Top3RankViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top3RankViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_top_3_rank, parent, false)
        return Top3RankViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: Top3RankViewHolder, position: Int) {
        val item = list[position]
        holder.bindItems(item, context)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int = list.size

    fun addItems(items : ArrayList<Coin>) {
        val lastPos = list.size - 1
        list = items
        notifyItemRangeInserted(lastPos, items.size)
    }

    fun resetItems() {
        val size: Int = list.size
        if (size > 0) {
            for (i in 0 until size) {
                list.removeAt(0)
            }
            notifyItemRangeRemoved(0, size)
        }
    }

    interface OnItemClickListener {
        fun onClick(item: Coin)
    }
}