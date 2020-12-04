package com.example.mybudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import com.example.mybudget.Adapters.CurrencyListAdapter
import com.example.mybudget.Global.Companion.DEBT_CURRENCY
import com.example.mybudget.Global.Companion.GOAL
import com.example.mybudget.Global.Companion.GOAL_CURRENCY
import com.example.mybudget.Global.Companion.UP
import kotlinx.android.synthetic.main.activity_add_goal.*
import kotlinx.android.synthetic.main.activity_calculator.*
import kotlinx.android.synthetic.main.activity_currency_picker.*
import kotlinx.android.synthetic.main.activity_currency_picker.toolbar

class CurrencyPicker : AppCompatActivity() {
    val nameCurrency = arrayOf<String>(
        "Euro",
        "United States Dollar",
        "British Pound",
        "Czech Koruna",
        "Turkish Lira",
        "Emirati Dirham",
        "Afghanistan Afghani",
        "Argentine Peso",
        "Australian Dollar",
        "Barbados Dollar",
        "Bangladeshi Taka",
        "Bulgarian Lev",
        "Bahraini Dinar",
        "Bermuda Dollar",
        "Brunei Darussalam Dollar",
        "Bolivia Bolíviano",
        "Brazil Real",
        "Bhutanese Ngultrum",
        "Belize Dollar",
        "Canada Dollar",
        "Switzerland Franc",
        "Chile Peso",
        "China Yuan Renminbi",
        "Colombia Peso",
        "Costa Rica Colon",
        "Denmark Krone",
        "Dominican Republic Peso",
        "Egypt Pound",
        "Ethiopian Birr",
        "Georgian Lari",
        "Ghana Cedi",
        "Gambian dalasi",
        "Guyana Dollar",
        "Hong Kong Dollar",
        "Croatia Kuna",
        "Hungary Forint",
        "Indonesia Rupiah",
        "Israel Shekel",
        "Indian Rupee",
        "Iceland Krona",
        "Jamaica Dollar",
        "Japanese Yen",
        "Kenyan Shilling",
        "Korea (South) Won",
        "Kuwaiti Dinar",
        "Cayman Islands Dollar",
        "Kazakhstan Tenge",
        "Laos Kip",
        "Sri Lanka Rupee",
        "Liberia Dollar",
        "Lithuanian Litas",
        "Moroccan Dirham","MAD",
        "Moldovan Leu",
        "Macedonia Denar",
        "Mongolia Tughrik",
        "Mauritius Rupee",
        "Malawian Kwacha",
        "Mexico Peso",
        "Malaysia Ringgit",
        "Mozambique Metical",
        "Namibia Dollar",
        "Nigeria Naira",
        "Nicaragua Cordoba",
        "Norway Krone","kr",
        "Nepal Rupee",
        "New Zealand Dollar",
        "Oman Rial",
        "Peru Sol",
        "Papua New Guinean Kina",
        "Philippines Peso",
        "Pakistan Rupee",
        "Poland Zloty",
        "Paraguay Guarani",
        "Qatar Riyal",
        "Romania Leu",
        "Serbia Dinar",
        "Russia Ruble",
        "Saudi Arabia Riyal",
        "Sweden Krona",
        "Singapore Dollar",
        "Somalia Shilling",
        "Suriname Dollar",
        "Thailand Baht",
        "Trinidad and Tobago Dollar",
        "Taiwan New Dollar",
        "Tanzanian Shilling",
        "Ukraine Hryvnia",
        "Ugandan Shilling",
        "Uruguay Peso",
        "Venezuela Bolívar",
        "Viet Nam Dong",
        "Yemen Rial",
        "South Africa Rand"
    )

    val codeCurrency = arrayOf<String>(
        "EUR",
        "USD",
        "GBP",
        "CZK",
        "TRY",
        "AED",
        "AFN",
        "ARS",
        "AUD",
        "BBD",
        "BDT",
        "BGN",
        "BHD",
        "BMD",
        "BND",
        "BOB",
        "BRL",
        "BTN",
        "BZD",
        "CAD",
        "CHF",
        "CLP",
        "CNY",
        "COP",
        "CRC",
        "DKK",
        "DOP",
        "EGP",
        "ETB",
        "GEL",
        "GHS",
        "GMD",
        "GYD",
        "HKD",
        "HRK",
        "HUF",
        "IDR",
        "ILS",
        "INR",
        "ISK",
        "JMD",
        "JPY",
        "KES",
        "KRW",
        "KWD",
        "KYD",
        "KZT",
        "LAK",
        "LKR",
        "LRD",
        "LTL",
        "MAD",
        "MDL",
        "MKD",
        "MNT",
        "MUR",
        "MWK",
        "MXN",
        "MYR",
        "MZN",
        "NAD",
        "NGN",
        "NIO",
        "NOK",
        "NPR",
        "NZD",
        "OMR",
        "PEN",
        "PGK",
        "PHP",
        "PKR",
        "PLN",
        "PYG",
        "QAR",
        "RON",
        "RSD",
        "RUB",
        "SAR",
        "SEK",
        "SGD",
        "SOS",
        "SRD",
        "THB",
        "TTD",
        "TWD",
        "TZS",
        "UAH",
        "UGX",
        "UYU",
        "VEF",
        "VND",
        "YER",
        "ZAR"
    )

    val imageIdCurrency = arrayOf<Int>(
        R.drawable.flag_eur,
        R.drawable.flag_usd,
        R.drawable.flag_gbp,
        R.drawable.flag_czk,
        R.drawable.flag_try,
        R.drawable.flag_aed,
        R.drawable.flag_afn,
        R.drawable.flag_ars,
        R.drawable.flag_aud,
        R.drawable.flag_bbd,
        R.drawable.flag_bdt,
        R.drawable.flag_bgn,
        R.drawable.flag_bhd,
        R.drawable.flag_bmd,
        R.drawable.flag_bnd,
        R.drawable.flag_bob,
        R.drawable.flag_brl,
        R.drawable.flag_btn,
        R.drawable.flag_bzd,
        R.drawable.flag_cad,
        R.drawable.flag_chf,
        R.drawable.flag_clp,
        R.drawable.flag_cny,
        R.drawable.flag_cop,
        R.drawable.flag_crc,
        R.drawable.flag_dkk,
        R.drawable.flag_dop,
        R.drawable.flag_egp,
        R.drawable.flag_etb,
        R.drawable.flag_gel,
        R.drawable.flag_ghs,
        R.drawable.flag_gmd,
        R.drawable.flag_gyd,
        R.drawable.flag_hkd,
        R.drawable.flag_hrk,
        R.drawable.flag_huf,
        R.drawable.flag_idr,
        R.drawable.flag_ils,
        R.drawable.flag_inr,
        R.drawable.flag_isk,
        R.drawable.flag_jmd,
        R.drawable.flag_jpy,
        R.drawable.flag_kes,
        R.drawable.flag_krw,
        R.drawable.flag_kwd,
        R.drawable.flag_kyd,
        R.drawable.flag_kzt,
        R.drawable.flag_lak,
        R.drawable.flag_lkr,
        R.drawable.flag_lrd,
        R.drawable.flag_ltl,
        R.drawable.flag_mad,
        R.drawable.flag_mdl,
        R.drawable.flag_mkd,
        R.drawable.flag_mnt,
        R.drawable.flag_mur,
        R.drawable.flag_mwk,
        R.drawable.flag_mxn,
        R.drawable.flag_myr,
        R.drawable.flag_mzn,
        R.drawable.flag_nad,
        R.drawable.flag_ngn,
        R.drawable.flag_nio,
        R.drawable.flag_nok,
        R.drawable.flag_npr,
        R.drawable.flag_nzd,
        R.drawable.flag_omr,
        R.drawable.flag_pen,
        R.drawable.flag_pgk,
        R.drawable.flag_php,
        R.drawable.flag_pkr,
        R.drawable.flag_pln,
        R.drawable.flag_pyg,
        R.drawable.flag_qar,
        R.drawable.flag_ron,
        R.drawable.flag_rsd,
        R.drawable.flag_rub,
        R.drawable.flag_sar,
        R.drawable.flag_sek,
        R.drawable.flag_sgd,
        R.drawable.flag_sos,
        R.drawable.flag_srd,
        R.drawable.flag_thb,
        R.drawable.flag_ttd,
        R.drawable.flag_twd,
        R.drawable.flag_tzs,
        R.drawable.flag_uah,
        R.drawable.flag_ugx,
        R.drawable.flag_uyu,
        R.drawable.flag_vef,
        R.drawable.flag_vnd,
        R.drawable.flag_yer,
        R.drawable.flag_zar
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_picker)

        setupToolbar()

        val myListAdapter = CurrencyListAdapter(this, nameCurrency, codeCurrency, imageIdCurrency)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if(UP.equals("AddGoal")){
                val intent = Intent(this@CurrencyPicker, AddGoal::class.java)
                GOAL_CURRENCY =  codeCurrency[position]
                startActivity(intent)
            } else if(UP.equals("AddDebt")){
                val intent = Intent(this@CurrencyPicker, AddDebt::class.java)
                DEBT_CURRENCY =  codeCurrency[position]
                startActivity(intent)
            }
        }
    }
    private fun setupToolbar(){
        toolbar.setNavigationOnClickListener {
            if(UP.equals("AddGoal")){
                val intent = Intent(this@CurrencyPicker, AddGoal::class.java)
                startActivity(intent)
            } else if(UP.equals("AddDebt")){
            val intent = Intent(this@CurrencyPicker, AddDebt::class.java)
            startActivity(intent)
        }
        }

        toolbar.inflateMenu(R.menu.menu5)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }
}