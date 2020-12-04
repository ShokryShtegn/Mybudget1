package com.example.mybudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Spinner
import android.widget.TextView
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_DATE
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_MEMO
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_TAYPE
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_TIME
import kotlinx.android.synthetic.main.activity_ongoing_goal_info.*

class AddGoalTransactin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal_transactin)

        val mType = findViewById<Spinner>(R.id.type_value_spinner)
        val mDate = findViewById<TextView>(R.id.date_value)
        val mTime = findViewById<TextView>(R.id.time_value)
        val mAmount = findViewById<TextView>(R.id.amount_value)
        val mWallet = findViewById<TextView>(R.id.wallet_value)
        val mMemo = findViewById<TextView>(R.id.memo_text)


        toolbar.inflateMenu(R.menu.menu4)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, OngoingGoalInfo::class.java)
            startActivity(intent)
        }

        if(GOAL_TRANSACTION){
            Global.initGoal()
        }

        if(GOAL_TRANSACTION_TAYPE == 0){
            //mName.setText(Global.GOAL_NAME)
        } else{

        }
        mDate.setText(GOAL_TRANSACTION_DATE)
        mTime.setText(GOAL_TRANSACTION_TIME)
        mAmount.setText(GOAL_TRANSACTION_AMOUNT.toString())
        mWallet.setText("")
        mMemo.setText(GOAL_TRANSACTION_MEMO)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.save -> {
                //Toast.makeText(this@AddGoal, "Save Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Goal::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}