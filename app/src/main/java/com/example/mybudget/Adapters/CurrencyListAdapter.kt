package com.example.mybudget.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mybudget.R

class CurrencyListAdapter(private val context: Activity, private val title: Array<String>, private val symbol: Array<String>, private val imgid: Array<Int>)
    : ArrayAdapter<String>(context, R.layout.currency_row, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.currency_row, null, true)

        val titleText = rowView.findViewById(R.id.row_title) as TextView
        val symbolText = rowView.findViewById(R.id.row_symbol) as TextView
        val imageView = rowView.findViewById(R.id.row_icon) as ImageView

        titleText.text = title[position]
        symbolText.text = symbol[position]
        imageView.setImageResource(imgid[position])

        return rowView
    }
}