package com.example.mybudget

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mybudget.Adapters.WalletListAdapter
import com.example.mybudget.Global.Companion.DEBT_WALLET_ID
import com.example.mybudget.Global.Companion.GOAL_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_CURRENCY
import com.example.mybudget.Global.Companion.GOAL_WALLET
import com.example.mybudget.Global.Companion.UP
import com.example.mybudget.Global.Companion.USER_ID
import com.example.mybudget.Global.Companion.convertedValue
import kotlinx.android.synthetic.main.activity_currency_picker.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import com.example.mybudget.Wallets
import kotlinx.android.synthetic.main.converter_currency.*
import java.lang.Exception

class WalletPicker : AppCompatActivity() {
    companion object{
        var result = 0.0
    }
    var walletsList: MutableList<Wallets> = ArrayList()
    var nameWallet: MutableList<String> = ArrayList()
    var balanceWallet: MutableList<String> = ArrayList()
    var recommendWallet: MutableList<String> = ArrayList()
    var imageIdWallet: MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_picker)

        readFromLocalStorage()

        val myListAdapter = WalletListAdapter(this, nameWallet, balanceWallet, recommendWallet, imageIdWallet)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if(UP.equals("AddGoal")){
                GOAL_WALLET = walletsList[position].id
                val intent = Intent(this@WalletPicker, AddGoal::class.java)
                startActivity(intent)
            } else if(UP.equals("AddDebt")){
                DEBT_WALLET_ID = walletsList[position].id
                val intent = Intent(this@WalletPicker, AddDebt::class.java)
                startActivity(intent)
            }
        }

        setupToolbar()
    }

    private fun setupToolbar(){
        toolbar.inflateMenu(R.menu.menu5)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        toolbar.setNavigationOnClickListener {
            if(UP.equals("AddGoal")){
                val intent = Intent(this@WalletPicker, AddGoal::class.java)
                startActivity(intent)
            } else if(UP.equals("AddDebt")){
                val intent = Intent(this@WalletPicker, AddDebt::class.java)
                startActivity(intent)
            }
        }
    }

    private fun convertCurrency(amount: Double, convertFrom: String, convertTo: String): Double {
        var conversionRate = 0.0
        var conversionRate2 = 0.0
        result = amount

        var API = "http://data.fixer.io/api/latest?access_key=e39c7af32f6f1511d53d6a9b3d242b78&symbols=$convertFrom"
        var API2 = "http://data.fixer.io/api/latest?access_key=e39c7af32f6f1511d53d6a9b3d242b78&symbols=$convertTo"

        if (!convertFrom.equals(convertTo)) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiResult = URL(API).readText()
                    val apiResult2 = URL(API2).readText()
                    val jsonObject = JSONObject(apiResult)
                    val jsonObject2 = JSONObject(apiResult2)
                    conversionRate =
                        jsonObject.getJSONObject("rates").getString(convertFrom)
                            .toDouble()
                    conversionRate2 =
                        jsonObject2.getJSONObject("rates").getString(convertTo)
                            .toDouble()

                    withContext(Dispatchers.Main) {
                        val con1 = amount / conversionRate
                        val text = (con1 * conversionRate2).toString()
                        Log.d("Main", "$amount $convertFrom = $result $convertTo")
                        result = text.toDouble()
                    }
                } catch (e: Exception) {
                    Log.e("Main", "$e")
                }
            }
        }
        return result
    }


    public fun sortWallet(currency: String){
        val context = this@WalletPicker
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(this)
        val db: SQLiteDatabase = dataBaseHandler.readableDatabase
        walletsList = dataBaseHandler.readWallet(USER_ID)

        var temp = Wallets()
        for (i in 1 until walletsList.size) {
            for (j in i downTo 1) {
                if (convertCurrency(
                        walletsList.get(j).balance.toDouble(),
                        walletsList.get(j).currency,
                        currency
                    ) >
                    convertCurrency(
                        walletsList.get(j - 1).balance.toDouble(),
                        walletsList.get(j).currency,
                        currency
                    )
                ) {
                    temp = walletsList.get(j)
                    walletsList.set(j, walletsList.get(j - 1))
                    walletsList.set(j - 1, temp)
                }
            }
        }
        db.close()
    }

    private fun readFromLocalStorage() {
        sortWallet(GOAL_CURRENCY)
        var i: Int = 0
        val size = walletsList.size
        while(i < size) {
            nameWallet.add(walletsList.get(i).wallet_name)
            balanceWallet.add(convertCurrency(walletsList.get(i).balance.toDouble(), walletsList.get(i).currency, GOAL_CURRENCY).toString())
            if(convertCurrency(walletsList.get(i).balance.toDouble(), walletsList.get(i).currency, GOAL_CURRENCY) > GOAL_AMOUNT){
                recommendWallet.add("Recommend")
            } else{
                recommendWallet.add("")
            }
            imageIdWallet.add(R.drawable.ic_wallet)
            i++
        }
    }
}