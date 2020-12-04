package com.example.mybudget.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mybudget.*

/*
 * Created by Isra
 */

class ManageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val UserIDLog = bundle?.getString("User_id")
        Toast.makeText(context, UserIDLog.toString(), Toast.LENGTH_SHORT).show()

        val view: View = inflater.inflate(R.layout.fragment_manage, container, false)
        val context = activity as Context
        val goal_btn = view.findViewById<Button>(R.id.goal) as Button
        val event_btn = view.findViewById<Button>(R.id.Event) as Button
        val debt_btn = view.findViewById<Button>(R.id.Debt) as Button
        val show_btn = view.findViewById<Button?>(R.id.show_wallet) as? Button

        debt_btn.setOnClickListener({
            val intent = Intent(context, Debt::class.java)
            startActivity(intent)
        })

        if (show_btn != null) {
            show_btn.setOnClickListener({
                GlobalVariable.id_ui = 1
                //var uuid = GlobalVariable.id_ui.toString()
                val intent = Intent(context, Wallet::class.java)
                intent.putExtra("uuid", GlobalVariable.id_ui.toString())
                intent.putExtra("User_id", UserIDLog)
                startActivity(intent)
            })
        }

        goal_btn.setOnClickListener({
            val intent = Intent(context, Goal::class.java)
            startActivity(intent)
        })

        return view
    }
}