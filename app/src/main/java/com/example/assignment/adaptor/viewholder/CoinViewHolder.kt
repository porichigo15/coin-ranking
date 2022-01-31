package com.example.assignment.adaptor.viewholder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.model.Coin
import com.example.assignment.util.ConvertUtil
import com.example.assignment.util.loadSvg
import kotlin.math.pow

class CoinViewHolder(itemView: View, var listener: CoinListener) : RecyclerView.ViewHolder(itemView) {

    private val coinCard: LinearLayout = itemView.findViewById(R.id.coinCard)
    private val inviteFriendCard: LinearLayout = itemView.findViewById(R.id.inviteFriendCard)
    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val symbol: TextView = itemView.findViewById(R.id.symbol)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val change: TextView = itemView.findViewById(R.id.change)
    private val price: TextView = itemView.findViewById(R.id.price)

    fun bindItems(item: Coin, context: Context, index: Int, condition: Int) {
        itemView.apply {
            if (index > 0 && index == condition) {
                // Show invite friend card
                inviteFriendCard.visibility = View.VISIBLE
                coinCard.visibility = View.GONE

                inviteFriendCard.setOnClickListener {
                    listener.share(item.coinrankingUrl!!)
                }
            } else {
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

                if (item.price != null) {
                    price.text = ConvertUtil.convertCurrency(item.price!!, "00000")
                }

                if (item.iconUrl != null) {
                    icon.loadSvg(item.iconUrl!!)
                }

                // Show coin card
                coinCard.visibility = View.VISIBLE
                inviteFriendCard.visibility = View.GONE
            }
        }
    }

    interface CoinListener {
        fun share(url: String)
    }
}