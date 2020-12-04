package com.example.mybudget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.spinner_color_item.view.*


class ColorArrayAdapter(context: Context, colorList: List<Color>) : ArrayAdapter<Color>(context, 0, colorList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val color = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_color_item, parent, false)
        view.color_value.setImageResource(color!!.image)
        return view
    }
}