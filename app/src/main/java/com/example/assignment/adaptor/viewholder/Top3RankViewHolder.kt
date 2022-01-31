package com.example.assignment.adaptor.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.model.Coin
import com.example.assignment.util.loadSvg

class Top3RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val symbol: TextView = itemView.findViewById(R.id.symbol)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val change: TextView = itemView.findViewById(R.id.change)

    fun bindItems(item: Coin, context: Context) {
        itemView.apply {
            if (item.change != null) {
                val changeValue: Double = String.format("%.2f", item.change!!.toDouble()).toDouble()
                change.text = changeValue.toString()

                if (item.change!!.toDouble() > 0) {
                    change.setTextColor(ContextCompat.getColor(context, R.color.arrow_down))
                    change.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_downward, 0, 0, 0)
                } else {
                    change.setTextColor(ContextCompat.getColor(context, R.color.arrow_up))
                    change.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_upward, 0, 0, 0)
                }
            }

            if (item.symbol != null) {
                symbol.text = item.symbol
            }

            if (item.name != null) {
                name.text = item.name
            }

            if (item.iconUrl != null) {
                icon.loadSvg(item.iconUrl!!)
            }
        }
    }
}