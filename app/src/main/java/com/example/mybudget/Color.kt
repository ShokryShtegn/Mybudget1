package com.example.mybudget

data class Color(val image: Int)

object Colors {

    private val images = intArrayOf(
        R.drawable.red,
        R.drawable.pink,
        R.drawable.purple,
        R.drawable.deep_purple,
        R.drawable.indigo,
        R.drawable.blue,
        R.drawable.light_blue,
        R.drawable.cyan,
        R.drawable.teal,
        R.drawable.green,
        R.drawable.light_green,
        R.drawable.lime,
        R.drawable.yellow,
        R.drawable.amber,
        R.drawable.orange,
        R.drawable.deep_orange,
        R.drawable.brown,
        R.drawable.gray,
        R.drawable.blue_gray
    )

    var list: ArrayList<Color>? = null
    get() {

        if (field != null)
            return field

        field = ArrayList()
        for (i in images.indices) {

            val imageId = images[i]
            //val countryName = countries[i]

            val country = Color(imageId)
            field!!.add(country)
        }

        return field
    }
}

