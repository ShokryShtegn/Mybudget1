package com.example.mybudget

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.mybudget.Global.Companion.GOAL
import com.example.mybudget.Global.Companion.GOAL_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_CURRENCY
import com.example.mybudget.Global.Companion.GOAL_ID
import com.example.mybudget.Global.Companion.GOAL_NAME
import com.example.mybudget.Global.Companion.GOAL_TARGET_DATE
import com.example.mybudget.Global.Companion.GOAL_WALLET
import com.example.mybudget.Global.Companion.NOT_SYNCED_WITH_SERVER
import com.example.mybudget.Global.Companion.SYNCED_WITH_SERVER
import com.example.mybudget.Global.Companion.UP
import com.example.mybudget.Global.Companion.URL_ADD_GOAL
import com.example.mybudget.Global.Companion.USER_ID
import com.example.mybudget.Global.Companion.initGoal
import kotlinx.android.synthetic.main.activity_add_goal.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class AddGoal : AppCompatActivity() {

    companion object {
        var GOAL_COLOR: Int = 0
    }

    val context = this@AddGoal

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal)

        //goal_name.setText("isra")
        setupCustomSpinner()

        toolbar.setNavigationOnClickListener {
            initGoal()
            val intent = Intent(this@AddGoal, Goal::class.java)
            startActivity(intent)
        }

        toolbar.inflateMenu(R.menu.menu3)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        //amount_value.text = Global.first_entered_add_goal.toString()

        if(GOAL){
           initGoal()
        }

        goal_name.setText(GOAL_NAME)
        amount_value.setText(GOAL_AMOUNT.toString())
        amount_currency.setText(GOAL_CURRENCY)
        targat_date_value.setText(GOAL_TARGET_DATE)
        wallet_value.setText(GOAL_WALLET.toString())
        val position: Int = GOAL_COLOR
        position.let { color_value_spinner.setSelection(it) }

        if (!goal_name.text.toString().equals("") && !amount_value.text.toString().equals("0")) {
            toolbar.menu.findItem(R.id.save).isEnabled = true
        } else {
            toolbar.menu.findItem(R.id.save).isEnabled = false
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        targat_date_value.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    targat_date_value.setText("" + mYear + "-" + mMonth + "-" + mDay)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        amount_value.setOnClickListener {
            val intent = Intent(this@AddGoal, Calculator::class.java)
            UP = "AddGoal"
            GOAL = false
            GOAL_NAME = goal_name.text.toString()
            GOAL_AMOUNT =  amount_value.text.toString().toDouble()
            GOAL_CURRENCY = amount_currency.text.toString()
            GOAL_TARGET_DATE = targat_date_value.text.toString()
            GOAL_WALLET = wallet_value.text.toString().toInt()
            GOAL_COLOR = GOAL_COLOR
            startActivity(intent)
        }

        amount_currency.setOnClickListener {
            val intent = Intent(this@AddGoal, CurrencyPicker::class.java)
            UP = "AddGoal"
            GOAL = false
            GOAL_NAME = goal_name.text.toString()
            GOAL_AMOUNT =  amount_value.text.toString().toDouble()
            GOAL_CURRENCY = amount_currency.text.toString()
            GOAL_TARGET_DATE = targat_date_value.text.toString()
            GOAL_WALLET = wallet_value.text.toString().toInt()
            GOAL_COLOR = GOAL_COLOR
            startActivity(intent)
        }

        wallet_value.setOnClickListener {
            val intent = Intent(this@AddGoal, WalletPicker::class.java)
            UP = "AddGoal"
            GOAL = false
            GOAL_NAME = goal_name.text.toString()
            GOAL_AMOUNT =  amount_value.text.toString().toDouble()
            GOAL_CURRENCY = amount_currency.text.toString()
            GOAL_TARGET_DATE = targat_date_value.text.toString()
            GOAL_WALLET = wallet_value.text.toString().toInt()
            GOAL_COLOR = GOAL_COLOR
            startActivity(intent)
        }

        Toast.makeText(this, GOAL_ID.toString(), Toast.LENGTH_SHORT).show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.save -> {
                //Toast.makeText(this@AddGoal, "Save Is Selected", Toast.LENGTH_SHORT).show()
                submit()
                val intent = Intent(this@AddGoal, Goal::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupCustomSpinner() {
        val adapter = Colors.list?.let { ColorArrayAdapter(this, it) }
        color_value_spinner.adapter = adapter
        // TODO Assignment: add listener to the customSpinner
        color_value_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GOAL_COLOR = position
                val selectedItem = parent!!.getItemAtPosition(position)
                Toast.makeText(this@AddGoal, "$selectedItem Selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddGoal, "Select Color", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submit() {
        val name: String = goal_name.text.toString()
        val amount: Double = amount_value.text.toString().toDouble()
        val currency: String = amount_currency.text.toString()
        val targetDate: String = targat_date_value.text.toString()
        val wallet: Int = 1
        val color: Int = GOAL_COLOR
        val achieve: Int = 0
        val starred: Int = 0
        saveToAppServer(GOAL_ID, USER_ID, wallet, name, amount, currency, targetDate, color, achieve, starred)
        initGoal()
    }

    private fun saveToAppServer(
        id: Int, user: Int, wallet: Int, name: String, amount: Double, currency: String,
        targetDate: String, color: Int, achieve: Int, starred: Int
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
                                    user,
                                    wallet,
                                    name,
                                    amount,
                                    currency,
                                    targetDate,
                                    color,
                                    achieve,
                                    starred,
                                    SYNCED_WITH_SERVER
                                )
                                Toast.makeText(this@AddGoal, "ERROR FALSE", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveToLocalStorage(
                                    id,
                                    user,
                                    wallet,
                                    name,
                                    amount,
                                    currency,
                                    targetDate,
                                    color,
                                    achieve,
                                    starred,
                                    NOT_SYNCED_WITH_SERVER
                                )
                                Toast.makeText(this@AddGoal, "ERROR TRUE", Toast.LENGTH_SHORT)
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
                            user,
                            wallet,
                            name,
                            amount,
                            currency,
                            targetDate,
                            color,
                            achieve,
                            starred,
                            NOT_SYNCED_WITH_SERVER
                        )
                    }
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params.put("id", user.toString())
                    params.put("name", name)
                    params.put("amount", amount.toString())
                    params.put("currency", currency)
                    params.put("target_date", targetDate)
                    params.put("color", color.toString())
                    return params
                }
            }
        VolleySingleton.getInstance(this@AddGoal)!!.addToRequestQueue(stringRequest)
    }

    private fun saveToLocalStorage(
        id: Int, user: Int, wallet: Int, name: String, amount: Double, currency: String,
        targetDate: String, color: Int, achieve: Int, starred: Int, status: Int
    ) {

        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.writableDatabase
        dataBaseHandler.saveGoal(
            id,
            user,
            wallet,
            name,
            amount,
            currency,
            targetDate,
            color,
            achieve,
            starred,
            status
        )
        db.close()
    }
}