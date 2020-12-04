package com.example.mybudget

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.mybudget.Global.Companion.DEBT
import com.example.mybudget.Global.Companion.DEBT_AMOUNT
import com.example.mybudget.Global.Companion.DEBT_COLOR
import com.example.mybudget.Global.Companion.DEBT_CURRENCY
import com.example.mybudget.Global.Companion.DEBT_DATE
import com.example.mybudget.Global.Companion.DEBT_DESCRIPTION
import com.example.mybudget.Global.Companion.DEBT_ID
import com.example.mybudget.Global.Companion.DEBT_NAME
import com.example.mybudget.Global.Companion.DEBT_TIME
import com.example.mybudget.Global.Companion.DEBT_WALLET_ID
import com.example.mybudget.Global.Companion.NOT_SYNCED_WITH_SERVER
import com.example.mybudget.Global.Companion.SYNCED_WITH_SERVER
import com.example.mybudget.Global.Companion.UP
import com.example.mybudget.Global.Companion.USER_ID
import com.example.mybudget.Global.Companion.initDebt
import kotlinx.android.synthetic.main.activity_add_debt.*
import kotlinx.android.synthetic.main.activity_add_goal.*
import kotlinx.android.synthetic.main.activity_add_goal.amount_value
import kotlinx.android.synthetic.main.activity_add_goal.color_value_spinner
import kotlinx.android.synthetic.main.activity_add_goal.toolbar
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class  AddDebt : AppCompatActivity() {
    companion object {
        var COLOR: Int = 0
        var TYPE: Int = 0
    }
    val context = this@AddDebt
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)

        setupSpinner()
        setupCustomSpinner()
        setupToolbar()

        if(DEBT){
            initDebt()
        }

        name_value.setText(DEBT_NAME)
        description_value.setText(DEBT_DESCRIPTION)
        amount_value.setText(DEBT_AMOUNT.toString())
        currency_value.setText(DEBT_CURRENCY)
        date_value.setText(DEBT_DATE)
        time_value.setText(DEBT_TIME)
        wallet_name.setText(DEBT_WALLET_ID.toString())
        val position: Int = COLOR
        position.let { color_value_spinner.setSelection(it) }

        date_value.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    date_value.setText("" + mYear + "-" + mMonth + "-" + mDay)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        time_value.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                time_value.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        amount_value.setOnClickListener {
            val intent = Intent(this@AddDebt, Calculator::class.java)
            saveInfo()
            startActivity(intent)
        }

        currency_value.setOnClickListener{
            val intent = Intent(this@AddDebt, CurrencyPicker::class.java)
            saveInfo()
            startActivity(intent)
        }

        wallet_name.setOnClickListener {
            val intent = Intent(this@AddDebt, WalletPicker::class.java)
            saveInfo()
            startActivity(intent)
        }
    }

    private fun saveInfo(){
        UP = "AddDebt"
        DEBT = false
        DEBT_NAME = name_value.text.toString()
        DEBT_DESCRIPTION = description_value.text.toString()
        DEBT_AMOUNT = amount_value.text.toString().toDouble()
        DEBT_CURRENCY = currency_value.text.toString()
        DEBT_DATE = date_value.text.toString()
        DEBT_TIME = time_value.text.toString()
        DEBT_WALLET_ID = wallet_name.text.toString().toInt()
        DEBT_COLOR = COLOR
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupToolbar(){
        toolbar.setNavigationOnClickListener {
            initDebt()
            val intent = Intent(this@AddDebt, Debt::class.java)
            startActivity(intent)
        }

        toolbar.inflateMenu(R.menu.menu3)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        if (name_value.text.toString().equals("") && !amount_value.text.toString().equals("0")) {
            toolbar.menu.findItem(R.id.save).isEnabled = true
        } else {
            toolbar.menu.findItem(R.id.save).isEnabled = false
        }
    }

    private fun setupSpinner(){
        val mTypeSpinner = findViewById<Spinner>(R.id.type) as Spinner
        val list = arrayOf("Payable", "Receivable")
        mTypeSpinner.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            list
        )
        mTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TYPE = mTypeSpinner.selectedItemPosition
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }
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
                COLOR = position
                val selectedItem = parent!!.getItemAtPosition(position)
                Toast.makeText(context, "$selectedItem Selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "Select Color", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.save -> {
                submit()
                val intent = Intent(this@AddDebt, Debt::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun submit() {
        val name: String = name_value.text.toString()
        val description = description_value.text.toString()
        val amount: Double = amount_value.text.toString().toDouble()
        val currency: String = currency_value.text.toString()
        val date: String = date_value.text.toString()
        val time: String = time_value.text.toString()
        val wallet: Int = wallet_name.text.toString().toInt()
        val color: Int = COLOR

        saveToAppServer(
            DEBT_ID,
            USER_ID,
            wallet,
            TYPE,
            name,
            description,
            amount,
            currency,
            date,
            time,
            color
        )
        Global.initGoal()
    }

    private fun saveToAppServer(
        id: Int, user: Int, wallet: Int, type: Int, name: String?, description: String?, amount: Double, currency: String?, date: String?,
        time: String?, color: Int
    ) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Saving Debt...")
        progressDialog.show()
        val stringRequest: StringRequest =
            object : StringRequest(
                Request.Method.POST, Global.URL_ADD_GOAL,
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
                                    type,
                                    name,
                                    description,
                                    amount,
                                    currency,
                                    date,
                                    time,
                                    color,
                                    SYNCED_WITH_SERVER
                                )
                                Toast.makeText(this@AddDebt, "ERROR FALSE", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveToLocalStorage(
                                    id,
                                    user,
                                    wallet,
                                    type,
                                    name,
                                    description,
                                    amount,
                                    currency,
                                    date,
                                    time,
                                    color,
                                    NOT_SYNCED_WITH_SERVER
                                )
                                Toast.makeText(this@AddDebt, "ERROR TRUE", Toast.LENGTH_SHORT)
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
                            type,
                            name,
                            description,
                            amount,
                            currency,
                            date,
                            time,
                            color,
                            NOT_SYNCED_WITH_SERVER
                        )
                    }
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    return params
                }
            }
        VolleySingleton.getInstance(this@AddDebt)!!.addToRequestQueue(stringRequest)
    }

    private fun saveToLocalStorage(
        id: Int, user: Int, wallet: Int, type: Int, name: String?, description: String?, amount: Double, currency: String?, date: String?,
        time: String?, color: Int, status: Int
    ) {

        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.writableDatabase
        dataBaseHandler.saveDebt(
            id,
            user,
            wallet,
            type,
            name,
            description,
            amount,
            currency,
            date,
            time,
            color,
            status
        )
        db.close()
    }
}