package com.example.mybudget.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mybudget.*

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CalendarFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)
        val context = activity as Context
        //val goal_btn = view.findViewById<Button>(R.id.goal) as Button

        // target date spinner on click
        //goal_btn.setOnClickListener({
            //val intent = Intent(context, Goal::class.java)
            //startActivity(intent)
        //})

        return view
    }
}