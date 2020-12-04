package com.example.mybudget
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.widget.doOnTextChanged
import com.example.mybudget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.converter_currency.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception
import java.net.URL

class CurrencyConverter : AppCompatActivity() {

    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f
    var conversionRate2 = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.converter_currency)

        spinnerSetup()
        textChangedStuff()
    }

    private fun textChangedStuff() {
        et_firstConversion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    getApiResult()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Type a value", Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main", "Before Text Changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Main", "OnTextChanged")
            }

        })

    }

    private fun getApiResult() {
        if (et_firstConversion != null && et_firstConversion.text.isNotEmpty() && et_firstConversion.text.isNotBlank()) {
            Log.d(baseCurrency, "OnTextChanged")
            var API = "http://data.fixer.io/api/latest?access_key=e39c7af32f6f1511d53d6a9b3d242b78&symbols=$baseCurrency"
            var API2 = "http://data.fixer.io/api/latest?access_key=e39c7af32f6f1511d53d6a9b3d242b78&symbols=$convertedToCurrency"
            //"http://apilayer.net/api/live?access_key=2606f6bc2852c8fd4c92a29eea251d23&currencies=$convertedToCurrency&source=$baseCurrency&format=1"
            //"https://xecdapi.xe.com/v1/convert_from.json/?from=$baseCurrency&to=$convertedToCurrency&amount=$am"
            //"https://api.currencyfreaks.com/latest?apikey=454fbc110c594a08893e10e2338f211e&symbols=$convertedToCurrency"
            //"https://api.ratesapi.io/api/latest?base=$baseCurrency&symbols=$convertedToCurrency"

            if (baseCurrency == convertedToCurrency) {
                Toast.makeText(
                    applicationContext,
                    "Please pick a currency to convert",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                GlobalScope.launch(Dispatchers.IO) {

                    try {

                        val apiResult = URL(API).readText()
                        val apiResult2 = URL(API2).readText()
                        val jsonObject = JSONObject(apiResult)
                        val jsonObject2 = JSONObject(apiResult2)
                        conversionRate =
                            jsonObject.getJSONObject("rates").getString(baseCurrency)
                                .toFloat()
                        conversionRate2 =
                            jsonObject2.getJSONObject("rates").getString(convertedToCurrency)
                                .toFloat()

                        Log.d("Main", "$conversionRate")
                        Log.d("Main", apiResult)

                        Log.d("Main", "$conversionRate2")
                        Log.d("Main", apiResult2)

                        withContext(Dispatchers.Main) {
                            val con1 = (et_firstConversion.text.toString().toFloat())/conversionRate
                            val text =
                                (con1 * conversionRate2).toString()
                            et_secondConversion?.setText(text)

                        }

                    } catch (e: Exception) {
                        Log.e("Main", "$e")
                    }
                }
            }
        }
    }

    private fun spinnerSetup() {
        val spinner: Spinner = findViewById(R.id.spinner_firstConversion)
        val spinner2: Spinner = findViewById(R.id.spinner_secondConversion)

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies2,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner2.adapter = adapter

        }

        spinner.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

        })

        spinner2.onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

        })
    }
}