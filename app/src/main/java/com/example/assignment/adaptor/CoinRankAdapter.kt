package com.example.assignment.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.adaptor.viewholder.CoinViewHolder
import com.example.assignment.model.Coin
import kotlin.math.pow

class CoinRankAdapter(
    var list: ArrayList<Coin>,
    var coinListener: CoinViewHolder.CoinListener,
    private var onItemClickListener: OnItemClickListener,
    var context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var round = 1
    private var condition = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coins, parent, false)
        return CoinViewHolder(view, coinListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder as CoinViewHolder

        condition = (5 * (2.0).pow(round - 1)).toInt()

        holder.bindItems(item, context, position, condition)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(item)
        }
        if (position == condition) {
            round++
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