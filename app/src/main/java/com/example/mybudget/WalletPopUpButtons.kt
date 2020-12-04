package com.example.mybudget

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import kotlinx.android.synthetic.main.wallet_dialog_buttons.*

class WalletPopUpButtons : AppCompatActivity() {
    private var popupTitle = ""
    private var popupText = ""
    private var popupButton = ""
    private var darkStatusBar = false
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
    var wallet= Wallets()
    val db = DataBaseHandler(context)
    var ID_Of_User: String = ""
    var walletID: String = ""
    var wallet_User_ID: String = ""
    var walletName: String = ""
    var walletType: String = ""
    var walletCurrency: String = ""
    var walletBalance: String = ""
    var walletFlag: Boolean = false
    var idAfterPopUp: String = ""
    var Done: String = ""
    var wallet_id: String = ""

    var walletButtonName: String = ""
    var walletButtonCardType: String = ""
    var walletButtonCurrencyType: String = ""
    var walletButtonbalance: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(com.example.mybudget.R.layout.wallet_dialog_buttons)

        var intent6 = getIntent()
        walletButtonCardType = intent6.getStringExtra("card_type").toString()
        supportActionBar?.title = walletButtonCardType
        Toast.makeText(context, "walletButtonCardType In begin: " + walletButtonCardType, Toast.LENGTH_SHORT).show()

        var intent7 = getIntent()
        walletButtonCurrencyType = intent7.getStringExtra("currency_type").toString()
        supportActionBar?.title = walletButtonCurrencyType
        Toast.makeText(context, "walletButtonCurrencyType In begin: " + walletButtonCurrencyType, Toast.LENGTH_SHORT).show()

        // var l: LinearLayout = findViewById(R.)
        // Get the data
        val bundle = intent.extras

        val context = this@WalletPopUpButtons
        if(walletButtonCardType.equals("Cash", false)){
            cardArray = arrayOf<String?>(s1, s2)
        }else if(walletButtonCardType.equals("Card", false)){
            cardArray = arrayOf<String?>(s2, s1)
        }else{
            cardArray = arrayOf<String?>(s1, s2)
        }
        if(walletButtonCurrencyType.equals("Dollar", false)){
            currencyArray = arrayOf<String?>(s3, s4, s5)
        }else if(walletButtonCurrencyType.equals("Shekel", false)){
            currencyArray = arrayOf<String?>(s4, s5, s3)
        }else if(walletButtonCurrencyType.equals("Jordanian Dinar", false)){
            currencyArray = arrayOf<String?>(s5, s4, s3)
        }else{
            currencyArray = arrayOf<String?>(s3, s4, s5)
        }
        //val names = arrayOf("Isra 1", "Isra 2")
        card_type.adapter = ArrayAdapter<String>(
            context,
            R.layout.simple_list_item_1,
            cardArray
        )
        //Toast.makeText(context,  R.layout.simple_list_item_1.toString(), Toast.LENGTH_SHORT).show()
        currency_type.adapter = ArrayAdapter<String>(
            context,
            R.layout.simple_list_item_1,
            currencyArray
        )
        // Set the Status bar appearance for different API levels
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }

        card_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            val context = this@WalletPopUpButtons
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = card_type.selectedItem.toString()
                Toast.makeText(context, "Card Type is: "+result, Toast.LENGTH_SHORT).show()
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }
        currency_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            val context = this@WalletPopUpButtons
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result2 = currency_type.selectedItem.toString()
                Toast.makeText(context, "Currency Type is: " + result2, Toast.LENGTH_SHORT).show()
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }
        var intent3 = getIntent()
        ID_Of_User = intent3.getStringExtra("User_id").toString()
        supportActionBar?.title = ID_Of_User
        Toast.makeText(context, "ID Of User In begin: " + ID_Of_User, Toast.LENGTH_SHORT).show()
        idAfterPopUp = ID_Of_User

        var intent4 = getIntent()
        wallet_id = intent4.getStringExtra("Wallet_id").toString()
        supportActionBar?.title = wallet_id
        Toast.makeText(context, "ID Of wallet In begin: " + wallet_id, Toast.LENGTH_SHORT).show()

        var intent5 = getIntent()
        walletButtonName = intent5.getStringExtra("Wallet_name").toString()
        supportActionBar?.title = walletButtonName
        Toast.makeText(context, "walletButtonName In begin: " + walletButtonName, Toast.LENGTH_SHORT).show()


        var intent8 = getIntent()
        walletButtonbalance = intent8.getStringExtra("balance").toString()
        supportActionBar?.title = walletButtonbalance
        Toast.makeText(context, "walletButtonbalance In begin: " + walletButtonbalance, Toast.LENGTH_SHORT).show()

        wallet_name.setText(walletButtonName)
        balance.setText(walletButtonbalance)

        save.setOnClickListener({
            cardName = wallet_name.text.toString()

            cardTypeS = result
            currencyTypeS = result2

            balanceS = balance.text.toString().toFloat()
            //Toast.makeText(this, cardName, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, cardTypeS, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, currencyTypeS, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, balanceS.toString(), Toast.LENGTH_SHORT).show()

            var dU = db.readData()
            for (j in 0..(dU.size - 1)) {
                Toast.makeText(context, "ID of all users is: " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "ID of user is: " + ID_Of_User, Toast.LENGTH_SHORT).show()
                if (dU[j].id == ID_Of_User.toInt()) {
                    var data = db.readWallet(ID_Of_User.toInt())
                    if (data.size == 0) {
                        if ((wallet_name.text.toString().isNotEmpty()) &&
                            (card_type.toString().isNotEmpty()) &&
                            (currency_type.toString().isNotEmpty()) &&
                            (balance.text.toString().isNotEmpty())) {
                            Toast.makeText(context, "ID of this user is: " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                            var wallet = Wallets(
                                cardName,
                                cardTypeS,
                                currencyTypeS,
                                balanceS.toString(),
                                false
                            )
                            db.updateWallet(wallet, wallet_id.toInt())
                            var data2 = db.readWallet(ID_Of_User.toInt())
                            Toast.makeText(context, "Hello From if: " + data2.get(0).wallet_User_ID, Toast.LENGTH_SHORT).show()
                            //Toast.makeText(context, "ID: " + data2.get(i).id.toString() + " name: " + data2.get(i).wallet_name + " type: " + data2.get(i).wallet_type + " currency: " + data2.get(i).currency + " balance:  " + data2.get(i).balance + " flag " + data2.get(i).include_flag, Toast.LENGTH_SHORT).show()
                            //showWallet.append("ID: " + data2.get(i).id.toString() + " name: " + data2.get(i).wallet_name + " type: " + data2.get(i).wallet_type + " currency: " + data2.get(i).currency + " balance:  " + data2.get(i).balance + " flag " + data2.get(i).include_flag)
                            walletID = (data2.get(0).id).toString()
                            wallet_User_ID = (data2.get(0).wallet_User_ID).toString()
                            walletName = data2.get(0).wallet_name
                            walletType = data2.get(0).wallet_type
                            walletCurrency = data2.get(0).currency
                            walletBalance = data2.get(0).balance
                            walletFlag = data2.get(0).include_flag
                            Done = "done"
                            Log.d("Wallet", "Selected")
                            val intent = Intent(this, Wallet::class.java)
                            intent.putExtra("Done", Done)
                            intent.putExtra("cardType", result)
                            intent.putExtra("walletID", walletID)
                            intent.putExtra("idAfterPopUp", idAfterPopUp)
                            intent.putExtra("wallet_User_ID", wallet_User_ID)
                            intent.putExtra("walletName", walletName)
                            intent.putExtra("walletType", walletType)
                            intent.putExtra("walletCurrency", walletCurrency)
                            intent.putExtra("walletBalance", walletBalance)
                            intent.putExtra("walletFlag", walletFlag)
                            startActivity(intent)
                        }
                    }else {
                        for (i in 0..(data.size - 1)) {
                            if ((wallet_name.text.toString().isNotEmpty()) &&
                                (card_type.toString().isNotEmpty()) &&
                                (currency_type.toString().isNotEmpty()) &&
                                (balance.text.toString().isNotEmpty())
                            ) {
                                Toast.makeText(context, "helllllllllll", Toast.LENGTH_SHORT).show()
                                var wallet = Wallets(
                                    cardName,
                                    cardTypeS,
                                    currencyTypeS,
                                    balanceS.toString(),
                                    false
                                )
                                db.updateWallet(wallet, wallet_id.toInt())
                                var data3 = db.readWallet(ID_Of_User.toInt())
                                for (i in 0..(data3.size - 1)) {
                                    Toast.makeText(context, "hiiiiiiiiii", Toast.LENGTH_SHORT)
                                        .show()
                                    if ((data3.get(i).wallet_User_ID == ID_Of_User.toInt())
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "ID: " + data3.get(i).id.toString() + " name: " + data3.get(
                                                i
                                            ).wallet_name + " type: " + data3.get(i).wallet_type + " currency: " + data3.get(
                                                i
                                            ).currency + " balance:  " + data3.get(i).balance + " flag " + data3.get(
                                                i
                                            ).include_flag,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Toast.makeText(
                                            context,
                                            "Hello From if2: " + data3.get(i).wallet_User_ID,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        //showWallet.append("ID: " + data2.get(i).id.toString() + " name: " + data2.get(i).wallet_name + " type: " + data2.get(i).wallet_type + " currency: " + data2.get(i).currency + " balance:  " + data2.get(i).balance + " flag " + data2.get(i).include_flag)
                                        walletID = (data3.get(i).id).toString()
                                        wallet_User_ID = (data3.get(i).wallet_User_ID).toString()
                                        walletName = data3.get(i).wallet_name
                                        walletType = data3.get(i).wallet_type
                                        walletCurrency = data3.get(i).currency
                                        walletBalance = data3.get(i).balance
                                        walletFlag = data3.get(i).include_flag
                                        Done = "done"
                                    }
                                }
                                //show.append("ID: " + data2.get(i).id.toString() + " name: " + data2.get(i).wallet_name + " type: " + data2.get(i).wallet_type + " currency: " + data2.get(i).currency + " balance:  " + data2.get(i).balance + " flag " + data2.get(i).include_flag)
                                /*var w = Wallet()
                    val N = 10 // total number of textviews to add
                    val myTextViews = arrayOfNulls<TextView>(N) // create an empty array;


                    for (i in 0 until N) {
                        // create a new textview
                        val rowTextView = TextView(this)

                        // set some properties of rowTextView or something
                        if (myTextViews[i].toString().equals(null)) {
                            rowTextView.text =
                                "ID: " + walletID + "\n" + " name: " + walletName + "\n" + " type: " + walletType + "\n" + " currency: " + walletCurrency + "\n" + " balance:  " + walletBalance + "\n" + " flag " + walletFlag + "\n"
                        }
                        // add the textview to the linearlayout
                        w.Lay.addView(rowTextView)

                        // save a reference to the textview for later
                        myTextViews[i] = rowTextView
                    }*/
                                Log.d("Wallet", "Selected")
                                val intent = Intent(this, Wallet::class.java)
                                intent.putExtra("cardType", result)
                                intent.putExtra("walletID", walletID)
                                intent.putExtra("idAfterPopUp", idAfterPopUp)
                                intent.putExtra("wallet_User_ID", wallet_User_ID)
                                intent.putExtra("walletName", walletName)
                                intent.putExtra("walletType", walletType)
                                intent.putExtra("walletCurrency", walletCurrency)
                                intent.putExtra("walletBalance", walletBalance)
                                intent.putExtra("walletFlag", walletFlag)
                                startActivity(intent)
                                break
                            } else if ((wallet_name.text.toString().isNotEmpty()) ||
                                (card_type.toString().isNotEmpty()) ||
                                (currency_type.toString().isNotEmpty()) ||
                                (balance.text.toString().isNotEmpty())
                            ) {
                                Toast.makeText(this, "Please fill All Data's", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }

            /*val intent = Intent(this, Wallet::class.java)
                intent.putExtra("cardType", result)
                startActivity(intent)*/
        })

        cancel.setOnClickListener({
            var dU = db.readData()
            for (j in 0..(dU.size - 1)) {
                Toast.makeText(context, "ID of all users is: " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "ID of user is: " + ID_Of_User, Toast.LENGTH_SHORT).show()
                if (dU[j].id == ID_Of_User.toInt()) {
                    var data = db.readWallet(ID_Of_User.toInt())
                    for (i in 0..(data.size - 1)) {
                        if (wallet_id.equals(data[i].id.toString())) {
                            wallet_User_ID = data[i].wallet_User_ID.toString()
                            val intent = Intent(this, Wallet::class.java)
                            intent.putExtra("wallet_User_ID", wallet_User_ID)
                            startActivity(intent)
                        }
                    }
                }
            }
        })
        delete.setOnClickListener({
            var dU = db.readData()
            for (j in 0..(dU.size - 1)) {
                Toast.makeText(context, "ID of all users is: " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "ID of user is: " + ID_Of_User, Toast.LENGTH_SHORT).show()
                if (dU[j].id == ID_Of_User.toInt()) {
                    var data = db.readWallet(ID_Of_User.toInt())
                    for(i in 0 ..(data.size - 1)){
                        if(wallet_id.equals(data[i].id.toString())){
                            wallet_User_ID = data[i].wallet_User_ID.toString()
                            db.deleteWallet(wallet_id.toInt())
                            val intent = Intent(this, Wallet::class.java)
                            intent.putExtra("wallet_User_ID", wallet_User_ID)
                            startActivity(intent)
                        }
                    }
                }
            }
        })

    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }


    override fun onBackPressed() {
// Fade animation for the background of Popup Window when you press the back delete
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

// Fade animation for the Popup Window when you press the back delete
        popup_window_view_with_border.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

// After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }

}