package com.example.assignment.fragment

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.View

import android.app.Dialog
import android.graphics.Color
import android.os.Build

import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.assignment.R
import com.example.assignment.model.Coin
import com.example.assignment.util.ConvertUtil
import com.example.assignment.util.loadSvg

class BottomSheetFragment(private val item: Coin): BottomSheetDialogFragment() {

    private lateinit var name: TextView
    private lateinit var symbol: TextView
    private lateinit var price: TextView
    private lateinit var marketCap: TextView
    private lateinit var description: TextView
    private lateinit var icon: ImageView
    private lateinit var goToBtn: Button

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.layout_bottom_sheet, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(resources.getColor(R.color.white))

        initElement(contentView)
    }

    private fun initElement(view: View) {
        name = view.findViewById(R.id.name)
        symbol = view.findViewById(R.id.symbol)
        price = view.findViewById(R.id.price)
        marketCap = view.findViewById(R.id.marketCap)
        description = view.findViewById(R.id.description)
        goToBtn = view.findViewById(R.id.goToButton)
        icon = view.findViewById(R.id.icon)

        val symbolText = "(${item.symbol})"

        name.text = item.name
        name.setTextColor(Color.parseColor(item.color))

        symbol.text = symbolText
        price.text = ConvertUtil.convertCurrency(item.price!!, "00")
        marketCap.text = ConvertUtil.convertCurrencySuffix(item.marketCap!!, "00")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.text = Html.fromHtml(item.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            description.text = Html.fromHtml(item.description)
        }

        if (item.iconUrl != null) {
            icon.loadSvg(item.iconUrl!!)
        }

        goToBtn.setOnClickListener {
            ConvertUtil.openNewTabWindow(item.websiteUrl!!, context!!)
        }
    }
}