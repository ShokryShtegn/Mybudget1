package com.example.mybudget

import android.app.ProgressDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.mybudget.Global.Companion.DEBT_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_CURRENCY
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_DATE
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_GOAL_ID
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_ID
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_TAYPE
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_TIME
import com.example.mybudget.Global.Companion.NOT_SYNCED_WITH_SERVER
import com.example.mybudget.Global.Companion.SYNCED_WITH_SERVER
import com.example.mybudget.Global.Companion.UP
import com.example.mybudget.Global.Companion.URL_ADD_GOAL
import com.example.mybudget.Global.Companion.initGoalTransaction
import kotlinx.android.synthetic.main.activity_calculator.*
import kotlinx.android.synthetic.main.activity_calculator.toolbar
import net.objecthunter.exp4j.ExpressionBuilder
import org.json.JSONException
import org.json.JSONObject


class Calculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setupToolbar()
        // numbers
        zero.setOnClickListener{
            appendOnExpresstion("0", true)
        }
        triple_zero.setOnClickListener{
            appendOnExpresstion("000", true)
        }
        one.setOnClickListener{
            appendOnExpresstion("1", true)
        }
        two.setOnClickListener{
            appendOnExpresstion("2", true)
        }
        three.setOnClickListener{
            appendOnExpresstion("3", true)
        }
        four.setOnClickListener{
            appendOnExpresstion("4", true)
        }
        five.setOnClickListener{
            appendOnExpresstion("5", true)
        }
        six.setOnClickListener{
            appendOnExpresstion("6", true)
        }
        seven.setOnClickListener{
            appendOnExpresstion("7", true)
        }
        eight.setOnClickListener{
            appendOnExpresstion("8", true)
        }
        nine.setOnClickListener{
            appendOnExpresstion("9", true)
        }

        // operations
        plus.setOnClickListener{
            appendOnExpresstion("+", false)
        }
        minus.setOnClickListener{
            appendOnExpresstion("-", false)
        }
        multiply.setOnClickListener{
            appendOnExpresstion("*", false)
        }
        division.setOnClickListener{
            appendOnExpresstion("/", false)
        }

        equal.setOnClickListener{
            try{
                val expression = ExpressionBuilder(tv_expression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()

                if(result == longResult.toDouble()){
                    tv_expression.text = longResult.toString()
                } else{
                    tv_expression.text = result.toString()
                }
            } catch (e: Exception){
                Log.d("Exception", "Message :" + e.message)
            }
        }

        ic_backspace.setOnClickListener {
            val text = tv_expression.text.toString()
            if(text.isNotEmpty()) {
                tv_expression.text = text.drop(1)
            }
        }

        C.setOnClickListener {
            tv_expression.text = ""
        }

        ic_done.setOnClickListener{
            if(UP.equals("AddGoal")){
                val intent = Intent(this@Calculator, AddGoal::class.java)
                GOAL_AMOUNT = tv_expression.text.toString().toDouble()
                startActivity(intent)
            } else if(UP.equals("GoalInfo")){
                submit()
                val intent = Intent(this@Calculator, OngoingGoalInfo::class.java)
                startActivity(intent)
            } else if(UP.equals("AddDebt")){
                val intent = Intent(this@Calculator, AddDebt::class.java)
                DEBT_AMOUNT = tv_expression.text.toString().toDouble()
                startActivity(intent)
            }
        }

        toolbar.setNavigationOnClickListener {
            if(UP.equals("AddGoal")){
                val intent = Intent(this@Calculator, AddGoal::class.java)
                startActivity(intent)
            } else if(UP.equals("GoalInfo")){
                val intent = Intent(this@Calculator, OngoingGoalInfo::class.java)
                startActivity(intent)
            } else if(UP.equals("AddDebt")){
                val intent = Intent(this@Calculator, AddDebt::class.java)
                startActivity(intent)
            }
        }
    }

    private fun appendOnExpresstion(string: String, canClear: Boolean){
        if(canClear){
            tv_expression.append(string)
        } else{
            tv_expression.append("")
            tv_expression.append(string)
        }
    }

    private fun setupToolbar(){
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@Calculator, AddGoal::class.java)
            startActivity(intent)
        }
    }
    private fun submit() {
        GOAL_TRANSACTION_AMOUNT = tv_expression.text.toString().toDouble()
        saveToAppServer(GOAL_TRANSACTION_ID, GOAL_TRANSACTION_GOAL_ID, GOAL_TRANSACTION_TAYPE, GOAL_TRANSACTION_AMOUNT, GOAL_TRANSACTION_CURRENCY, GOAL_TRANSACTION_DATE, GOAL_TRANSACTION_TIME, "No Memo")
        Log.d("Main", "$GOAL_TRANSACTION_GOAL_ID")
        initGoalTransaction()
    }

    private fun saveToAppServer(
        id: Int, goal: Int, type: Int, amount: Double, currency: String, date: String, time: String, memo: String
    ) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Saving Goal...")
        progressDialog.show()
        val stringRequest: StringRequest =
            object : StringRequest(
                Request.Method.POST, URL_ADD_GOAL,
                object : Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        progressDialog.dismiss()
                        try {
                            val obj = JSONObject(response)
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveToLocalStorage(
                                    id,
                                    goal,
                                    type,
                                    amount,
                                    currency,
                                    date,
                                    time,
                                    memo,
                                    SYNCED_WITH_SERVER
                                )
                                Toast.makeText(this@Calculator, "ERROR FALSE", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveToLocalStorage(
                                    id,
                                    goal,
                                    type,
                                    amount,
                                    currency,
                                    date,
                                    time,
                                    memo,
                                    NOT_SYNCED_WITH_SERVER
                                )
                                Toast.makeText(this@Calculator, "ERROR TRUE", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        progressDialog.dismiss()
                        //on error storing the name to sqlite with status unsynced
                        saveToLocalStorage(
                            id,
                            goal,
                            type,
                            amount,
                            currency,
                            date,
                            time,
                            memo,
                            NOT_SYNCED_WITH_SERVER
                        )
                    }
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    return params
                }
            }
        VolleySingleton.getInstance(this@Calculator)!!.addToRequestQueue(stringRequest)
    }

    private fun saveToLocalStorage(
        id: Int, goal: Int, type: Int, amount: Double, currency: String, date: String, time: String, memo: String?, status: Int
    ) {

        val dataBaseHandler: DataBaseHandler = DataBaseHandler(this@Calculator)
        val db: SQLiteDatabase = dataBaseHandler.writableDatabase
        dataBaseHandler.saveGoalTransaction(
            id,
            goal,
            type,
            amount,
            currency,
            date,
            time,
            memo,
            status
        )
        db.close()
    }
}