package com.example.mybudget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.wallet_dialog.*



// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================
// ============================================================================== This Class Not Used ========================================================================================================


class AddWallet : AppCompatActivity() {
    var cardName: String=""
    var cardTypeS: String=""
    var currencyTypeS: String=""
    var balanceS: Float= 0.0f
    var result: String = ""
    var result2: String = ""
    var context: Context = this
    var s1: String = "Cash"
    var s2: String = "Card"
    var s3: String = "Dollar"
    var s4: String = "Shekel"
    var s5: String = "Jordanian Dinar"
    private var cardArray = arrayOf<String?>(s1, s2)
    private var currencyArray = arrayOf<String?>(s3, s4, s5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallet_dialog)
        var mSpinner: Spinner = findViewById<Spinner>(R.id.card_type) as Spinner
        var mSpinnerCurr: Spinner = findViewById<Spinner>(R.id.currency_type) as Spinner
        var adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            cardArray
        )
        var adapterCurr: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            currencyArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCurr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mSpinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            cardArray
        )
        mSpinnerCurr.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            currencyArray
        )
        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = mSpinner.selectedItem.toString()
                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
                Log.d("Hello", result.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }
        mSpinnerCurr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result2 = mSpinnerCurr.selectedItem.toString()
                Toast.makeText(context, result2.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
                Log.d("Hello", result2.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }
            save.setOnClickListener({
                cardName = wallet_name.text.toString()

                cardTypeS = result
                currencyTypeS = result2

                balanceS = balance.text.toString().toFloat()
                Toast.makeText(this, cardName, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, cardTypeS, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, currencyTypeS, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, balanceS.toString(), Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Wallet::class.java)
                intent.putExtra("cardType", result)
                startActivity(intent)
            })
            cancel.setOnClickListener({
                val intent = Intent(this, Wallet::class.java)
                startActivity(intent)
            })


    }
}