package com.example.mybudget.Adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mybudget.R

class WalletListAdapter(private val context: Activity, private val name: MutableList<String>, private val balance: MutableList<String>, private val recommend: MutableList<String>, private val imgid: MutableList<Int>)
    : ArrayAdapter<String>(context, R.layout.wallet_row, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.wallet_row, null, true)

        val nameText = rowView.findViewById(R.id.row_name) as TextView
        val recommendText = rowView.findViewById(R.id.row_recommend) as TextView
        val balanceText = rowView.findViewById(R.id.row_balance) as TextView
        val imageView = rowView.findViewById(R.id.row_icon) as ImageView

        nameText.text = name[position]
        recommendText.text = recommend[position]
        balanceText.text = balance[position]
        imageView.setImageResource(imgid[position])

        return rowView
    }
}