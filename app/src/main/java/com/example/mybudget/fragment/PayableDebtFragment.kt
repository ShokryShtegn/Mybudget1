package com.example.mybudget.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget.*
import com.example.mybudget.Adapters.ItemDebtAdapter
import com.example.mybudget.Global.Companion.DEBT_AMOUNT
import com.example.mybudget.Global.Companion.DEBT_COLOR
import com.example.mybudget.Global.Companion.DEBT_CURRENCY
import com.example.mybudget.Global.Companion.DEBT_DATE
import com.example.mybudget.Global.Companion.DEBT_DESCRIPTION
import com.example.mybudget.Global.Companion.DEBT_ID
import com.example.mybudget.Global.Companion.DEBT_NAME
import com.example.mybudget.Global.Companion.DEBT_STATUS
import com.example.mybudget.Global.Companion.DEBT_TIME
import com.example.mybudget.Global.Companion.DEBT_TYPE
import com.example.mybudget.Global.Companion.DEBT_USER_ID
import com.example.mybudget.Global.Companion.DEBT_WALLET_ID
import com.example.mybudget.Global.Companion.USER_ID
import com.example.mybudget.models.Debt


/**
 * created by Isra
 */
class PayableDebtFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: ItemDebtAdapter? = null
    private val debtsList: MutableList<Debt> = ArrayList()
    var listener: ItemDebtAdapter.RecyclerViewClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_payable_debt, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView
        val context = activity as Context
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager

        listener = object : ItemDebtAdapter.RecyclerViewClickListener {
            override fun onRowClick(view: View?, position: Int) {
                DEBT_ID = debtsList[position].getId()
                DEBT_USER_ID = debtsList[position].getUser()
                DEBT_WALLET_ID = debtsList[position].getWallet()
                DEBT_TYPE = debtsList[position].getType()
                DEBT_NAME = debtsList[position].getName()
                DEBT_DESCRIPTION = debtsList[position].getDescription()
                DEBT_AMOUNT =debtsList[position].getAmount()
                DEBT_CURRENCY = debtsList[position].getCurrency()!!
                DEBT_DATE = debtsList[position].getDate()!!
                DEBT_TIME = debtsList[position].getTime()!!
                DEBT_COLOR = debtsList[position].getColor()!!
                DEBT_STATUS = debtsList[position].getStatus()
                val intent = Intent(context, DebtInfo::class.java)
                startActivity(intent)
            }
        }
        readFromLocalStorage()
        return view
    }

    fun readFromLocalStorage() {
        val context = activity as Context
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.readableDatabase
        val cursor: Cursor? =  dataBaseHandler.getDebts(USER_ID)
        debtsList.clear()
        while(cursor?.moveToNext()!!){
            val id: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_ID))
            val user: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COl_DEBT_USER_ID))
            val wallet: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_WALLET_ID))
            val type: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_TYPE))
            val name: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_NAME))
            val description: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_DESCRIPTION))
            val amount: Double = cursor.getDouble(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_AMOUNT))
            val currency: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_CURRENCY))
            val date: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_DATE))
            val time: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_TIME))
            val color: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_COLOR))
            val status: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_DEBT_STATUS))

            if(type ==  0){
                debtsList.add(Debt(id, user, wallet, type, name, description, amount, currency, date, time, color, status))
            }
        }
        adapter?.notifyDataSetChanged()
        cursor.close()
        db.close()
        adapter = ItemDebtAdapter(debtsList, context, listener!!)
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }
}

