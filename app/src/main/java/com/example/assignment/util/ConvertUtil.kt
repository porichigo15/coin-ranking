package com.example.assignment.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import java.text.DecimalFormat

class ConvertUtil {

    companion object {
        fun convertCurrency(price: String, digit: String): String {
            val decimal = DecimalFormat("#,###.${digit}")
            return "$ ${decimal.format(price.toDouble())}"
        }

        fun convertCurrencySuffix(price: String, digit: String): String {
            val string = convertCurrency(price, digit)
            val suffix: String = when {
                price.count() < 7 -> "million"
                price.count() < 13 -> "billion"
                else -> "trillion"
            }
            return "$string $suffix"
        }

        fun openNewTabWindow(urls: String, context : Context) {
            val uris = Uri.parse(urls)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            context.startActivity(intents)
        }
    }
}