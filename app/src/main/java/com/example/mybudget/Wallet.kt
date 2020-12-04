package com.example.mybudget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.fragment_wallet.*


class Wallet() : AppCompatActivity() {

    var context: Context = this
    var ID_Of_User: String = ""
    var cardType: String = ""
    var walletID: String = ""
    var wallet_User_ID: String = ""
    var walletName: String = ""
    var walletType: String = ""
    var walletCurrency: String = ""
    var walletBalance: String = ""
    var walletFlag: Boolean = false
    var idAfterPopUp: String=""
    var id2: String=""
    var uuid: String = ""
    var N = 10000 // total number of textviews to add
    //var flag: Boolean = false
    val myTextViews = arrayOfNulls<TextView>(N) // create an empty array;
    var DoneV: String = ""
    var s1 = ""
    lateinit var rowButtonView: Button
    lateinit var rowButtonView2: Button
    var text: String = ""
    var walletI: String = ""
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wallet)
        val db = DataBaseHandler(context)
        var intentS = getIntent()
        uuid = intentS.getStringExtra("uuid").toString()
        supportActionBar?.title = uuid
        Toast.makeText(context, "ID_From_Managment is: " + uuid, Toast.LENGTH_SHORT).show()

        if(uuid == "1"){
            var intent8 = getIntent()
            ID_Of_User = intent8.getStringExtra("User_id").toString()
            supportActionBar?.title = ID_Of_User
            Toast.makeText(context, "ID_From_Wallet is: " + ID_Of_User, Toast.LENGTH_SHORT).show()
            GlobalVariable.id_ui = 2
            GlobalVariable.id_ui2 = ID_Of_User.toInt()

            var dU = db.readData()

            for (j in 0..(dU.size - 1)) {
                Toast.makeText(context, "ID: " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                if((dU[j].id).toString().equals(ID_Of_User)){
                    Toast.makeText(context, "ID --> " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(
                        context,
                        "ID_Of_User --> " + ID_Of_User.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    var data2 = db.readWallet(ID_Of_User.toInt())

                    for (k in 0 ..(data2.size - 1)) {

                        // create a new textview
                        rowButtonView = Button(this)

                        // set some properties of rowTextView or something
                        if(data2[k].currency.equals("Dollar", false)){
                            GlobalVariable.currSymbol = " $"
                        }else if(data2[k].currency.equals("Shekel", false)){
                            GlobalVariable.currSymbol = " ₪"
                        }else{
                            GlobalVariable.currSymbol = " JD"
                        }
                        rowButtonView.text = data2[k].wallet_name + "\n" + data2[k].wallet_type + "\n" + data2[k].balance + GlobalVariable.currSymbol + "\n"
                        // add the textview to the linearlayout
                        layoutForTextView.addView(rowButtonView)

                        // save a reference to the textview for later
                        myTextViews[k] = rowButtonView
                        if(k % 2 == 0){
                            myTextViews[k]?.setBackgroundColor(Color.BLACK)
                            myTextViews[k]?.setTextColor(Color.WHITE)
                        }else if(k % 2 != 0){
                            myTextViews[k]?.setBackgroundColor(Color.WHITE)
                            myTextViews[k]?.setTextColor(Color.BLACK)
                        }

                        /*if(data2.size%2 == 0){
                            for(t in 0 ..(data2.size/2) ){
                                if(k+1 != data2.size){
                                    var linears: LinearLayout = LinearLayout(this)
                                    linears.addView(myTextViews[k])
                                    linears.addView(myTextViews[k+1])
                                    layoutForTextView.addView(linears)
                                }
                            }
                        }*/


                    }
                    //var data3 = db.readWallet(ID_Of_User.toInt())
                    for(l in 0 ..(myTextViews.size - 1)){
                        myTextViews[l]?.setOnClickListener({
                            text = myTextViews[l]?.text.toString().replace('\n', ' ')
                            var splitArr: List<String> = text.split(" ")
                            var nm = splitArr.get(0)
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "From List: " + splitArr.get(0), Toast.LENGTH_SHORT).show()
                            for(m in 0 ..(data2.size - 1)){
                                if(nm.equals(data2[m].wallet_name)){
                                    walletI = data2[m].id.toString()
                                    //var I: Int = db.getWalletID(data2[m].wallet_name)
                                    if(GlobalVariable.id_ui==2) {
                                        val intent9 = Intent(this, WalletPopUpButtons::class.java)
                                        intent9.putExtra("User_id", GlobalVariable.id_ui2.toString())
                                        intent9.putExtra("Wallet_id", data2[m].id.toString())
                                        intent9.putExtra("Wallet_name", data2[m].wallet_name.toString())
                                        intent9.putExtra("card_type", data2[m].wallet_type.toString())
                                        intent9.putExtra("currency_type", data2[m].currency.toString())
                                        intent9.putExtra("balance", data2[m].balance.toString())
                                        startActivity(intent9)
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }

        add_wallet.setOnClickListener({
            if(GlobalVariable.id_ui==2) {
                val intent9 = Intent(this, WalletPopUp::class.java)
                intent9.putExtra("User_id", GlobalVariable.id_ui2.toString())
                startActivity(intent9)
            }
        })
        var intent12 = getIntent()
        DoneV = intent12.getStringExtra("Done").toString()
        supportActionBar?.title = DoneV
        Toast.makeText(context, DoneV, Toast.LENGTH_SHORT).show()


        var intent1 = getIntent()
        cardType = intent1.getStringExtra("cardType").toString()
        supportActionBar?.title = cardType

        var intent2 = getIntent()
        walletID = intent2.getStringExtra("walletID").toString()
        supportActionBar?.title = walletID
        var intent13 = getIntent()
        idAfterPopUp = intent13.getStringExtra("idAfterPopUp").toString()
        supportActionBar?.title = idAfterPopUp

        var intent10 = getIntent()
        wallet_User_ID = intent10.getStringExtra("wallet_User_ID").toString()
        supportActionBar?.title = wallet_User_ID
        Toast.makeText(context, "wallet_User_ID: " + wallet_User_ID, Toast.LENGTH_SHORT).show()


        var intent3 = getIntent()
        walletName = intent3.getStringExtra("walletName").toString()
        supportActionBar?.title = walletName

        var intent4 = getIntent()
        walletType = intent4.getStringExtra("walletType").toString()
        supportActionBar?.title = walletType

        var intent5 = getIntent()
        walletCurrency = intent5.getStringExtra("walletCurrency").toString()
        supportActionBar?.title = walletCurrency

        var intent6 = getIntent()
        walletBalance = intent6.getStringExtra("walletBalance").toString()
        supportActionBar?.title = walletBalance

        var intent7 = getIntent()
        walletFlag = intent7.getBooleanExtra("walletFlag", false)
        supportActionBar?.title = walletFlag.toString()


        Log.d("hhhhhhhhhh", cardType.toString())
        Toast.makeText(context, "Hello1", Toast.LENGTH_SHORT).show()
        var dU = db.readData()

        for (j in 0..(dU.size - 1)) {
            Toast.makeText(context, "ID: " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
            if((dU[j].id).toString().equals(wallet_User_ID)){
                Toast.makeText(context, "ID --> " + dU[j].id.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "ID_Of_User --> " + wallet_User_ID.toString(), Toast.LENGTH_SHORT).show()
                var data2 = db.readWallet(wallet_User_ID.toInt())
                var counter2 = 0

                for (z in 0 ..(data2.size - 1)) {

                    // create a new textview
                    rowButtonView2 = Button(this)

                    // set some properties of rowTextView or something
                    if(data2[z].currency.equals("Dollar", false)){
                        GlobalVariable.currSymbol2 = " $"
                    }else if(data2[z].currency.equals("Shekel", false)){
                        GlobalVariable.currSymbol2 = " ₪"
                    }else{
                        GlobalVariable.currSymbol2 = " JD"
                    }
                    rowButtonView2.text = data2[z].wallet_name + "\n" + data2[z].wallet_type + "\n" + data2[z].balance + GlobalVariable.currSymbol2 + "\n"

                    // add the textview to the linearlayout
                    layoutForTextView.addView(rowButtonView2)

                    // save a reference to the textview for later
                    myTextViews[z] = rowButtonView2
                    if(z % 2 == 0){
                        myTextViews[z]?.setBackgroundColor(Color.BLACK)
                        myTextViews[z]?.setTextColor(Color.WHITE)
                    }else if(z % 2 != 0){
                        myTextViews[z]?.setBackgroundColor(Color.WHITE)
                        myTextViews[z]?.setTextColor(Color.BLACK)
                    }


                }
                for(l in 0 ..(myTextViews.size - 1)){
                    myTextViews[l]?.setOnClickListener({
                        text = myTextViews[l]?.text.toString().replace('\n', ' ')
                        var splitArr: List<String> = text.split(" ")
                        var nm = splitArr.get(0)
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                        Toast.makeText(context, "From List: " + splitArr.get(0), Toast.LENGTH_SHORT).show()
                        for(m in 0 ..(data2.size - 1)){
                            if(nm.equals(data2[m].wallet_name)){
                                walletI = data2[m].id.toString()
                                if(GlobalVariable.id_ui==2) {
                                    val intent9 = Intent(this, WalletPopUpButtons::class.java)
                                    intent9.putExtra("User_id", GlobalVariable.id_ui2.toString())
                                    intent9.putExtra("Wallet_id", data2[m].id.toString())
                                    intent9.putExtra("Wallet_name", data2[m].wallet_name.toString())
                                    intent9.putExtra("card_type", data2[m].wallet_type.toString())
                                    intent9.putExtra("currency_type", data2[m].currency.toString())
                                    intent9.putExtra("balance", data2[m].balance.toString())
                                    startActivity(intent9)
                                }
                            }
                        }
                    })
                }
            }
        }
        back.setOnClickListener({
            val intent = Intent(this@Wallet, homeLight::class.java)
            intent.putExtra("DestWallet", "Manage")
            startActivity(intent)
        })
    }
}