package com.example.mybudget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter

class SliderAdapter(var c: Context): PagerAdapter() {
    var context: Context = c
    lateinit var layoutInFlater: LayoutInflater
    var s1: String = "Don't just save money, save for your future."
    var s2: String = "Start Small. Think Big, with a short- term goal."
    var s3: String = "Make a savings plan."
    private var slideImages = intArrayOf(R.drawable.money6, R.drawable.money7, R.drawable.money8)
    private var slideDesc = arrayOf<String?>(s1, s2, s3)

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInFlater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = layoutInFlater.inflate(R.layout.slide_layout, container, false)
        var slideImageView: ImageView = view.findViewById(R.id.imageView) as ImageView
        var slideDescription: TextView = view.findViewById(R.id.slide_description) as TextView

        slideImageView.setImageResource(slideImages[position])
        slideDescription.setText(slideDesc[position])
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}


