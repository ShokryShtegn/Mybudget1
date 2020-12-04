package com.example.mybudget

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.mybudget.Adapters.ItemAdapter
import com.example.mybudget.Adapters.SubItemAdapter
import com.example.mybudget.Global.Companion.GOAL
import com.example.mybudget.Global.Companion.GOAL_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_COLOR
import com.example.mybudget.Global.Companion.GOAL_CURRENCY
import com.example.mybudget.Global.Companion.GOAL_ID
import com.example.mybudget.Global.Companion.GOAL_NAME
import com.example.mybudget.Global.Companion.GOAL_TARGET_DATE
import com.example.mybudget.Global.Companion.GOAL_TRANSACTION_TAYPE
import com.example.mybudget.Global.Companion.UP
import com.example.mybudget.Global.Companion.daysLeft
import com.example.mybudget.Global.Companion.getDayOfMonth
import com.example.mybudget.Global.Companion.getDayOfWeek
import com.example.mybudget.Global.Companion.getMonthAndYear
import com.example.mybudget.Global.Companion.myColor
import com.example.mybudget.models.Item
import com.example.mybudget.models.SubItem
import kotlinx.android.synthetic.main.activity_ongoing_goal_info.*
import java.util.*

class OngoingGoalInfo : AppCompatActivity() {

    val dateList: MutableList<String> = ArrayList<String>()
    val totalAmountList: MutableList<Double> = ArrayList<Double>()
    val dayOfWeekList: MutableList<String> = ArrayList<String>()
    val dayOfMonthList: MutableList<String> = ArrayList<String>()
    val monthYearList: MutableList<String> = ArrayList<String>()
    val subItemList: MutableList<SubItem> = ArrayList<SubItem>()
    val itemList: MutableList<Item> = ArrayList<Item>()
    var listener: SubItemAdapter.SubRecyclerViewClickListener? = null
    var totalSaved = 0.0

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ongoing_goal_info)

        val mName = findViewById<TextView>(R.id.name)
        val mSaved = findViewById<TextView>(R.id.saved_value)
        val mRemain = findViewById<TextView>(R.id.remain_value)
        val mAmount = findViewById<TextView>(R.id.amount_value)
        val mTargetDate = findViewById<TextView>(R.id.target_date)
        val mDaysLeft = findViewById<TextView>(R.id.day_left)
        val mDeposit = findViewById<TextView>(R.id.deposit)
        val mWithdraw = findViewById<TextView>(R.id.withdraw)

        readFromLocalStorage()

        val rvItem: RecyclerView = findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this@OngoingGoalInfo)
        val itemAdapter = ItemAdapter(itemList)
        rvItem.setAdapter(itemAdapter)
        rvItem.setLayoutManager(layoutManager)

        val disabler: OnItemTouchListener = RecyclerViewDisabler()
        //rvItem.addOnItemTouchListener(disabler)

        progres_bar.max = 100
        val percent = (GOAL_AMOUNT - totalSaved)/100.0
        progres_bar.progress = percent.toInt()

        toolbar.inflateMenu(R.menu.menu4)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@OngoingGoalInfo, Goal::class.java)
            startActivity(intent)
        }

        mName.setText(GOAL_NAME)
        mSaved.setText(totalSaved.toString())
        mRemain.setText((GOAL_AMOUNT - totalSaved).toString())
        mAmount.setText(GOAL_AMOUNT.toString() + " " + GOAL_CURRENCY)
        mTargetDate.setText(GOAL_TARGET_DATE)
        mDaysLeft.setText(daysLeft(GOAL_TARGET_DATE) + " days left")
        mDeposit.setTextColor(getResources().getColor(myColor(GOAL_COLOR)))
        mWithdraw.setTextColor(getResources().getColor(myColor(GOAL_COLOR)))


        ic_deposit.setOnClickListener{
            UP = "GoalInfo"
            GOAL_TRANSACTION_TAYPE = 0
            Toast.makeText(this, GOAL_ID.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@OngoingGoalInfo, Calculator::class.java)
            startActivity(intent)
        }

        ic_withdrow.setOnClickListener{
            UP = "GoalInfo"
            GOAL_TRANSACTION_TAYPE = 1
            Toast.makeText(this, GOAL_ID.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@OngoingGoalInfo, Calculator::class.java)
            startActivity(intent)
        }

        listener = object : SubItemAdapter.SubRecyclerViewClickListener {
            override fun onRowClick(view: View?, position: Int) {
                val intent = Intent(this@OngoingGoalInfo, AddGoalTransactin::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.ic_edit -> {
                GOAL = false
                Toast.makeText(this, GOAL_ID.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this@OngoingGoalInfo, AddGoal::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readFromLocalStorage() {
        val context = this@OngoingGoalInfo
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.readableDatabase
        var cursor: Cursor? =  dataBaseHandler.getGoalTransactionGroupByDate(GOAL_ID)
        totalSaved = 0.0
        dateList.clear()
        dayOfMonthList.clear()
        dayOfWeekList.clear()
        monthYearList.clear()
        totalAmountList.clear()
        while(cursor?.moveToNext()!!){
            dateList.add(cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_DATE)))
        }
        cursor.close()
        var i = 0
        while (i < dateList.size){
            var result: Double = 0.0
            cursor =  dataBaseHandler.getGoalTransaction(GOAL_ID)
            subItemList.clear()
            while(cursor?.moveToNext()!!) {
                val type: Int = cursor!!.getInt(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_TYPE))
                val amount: Double = cursor!!.getDouble(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_AMOUNT))
                val currency: String = cursor!!.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_CURRENCY))
                val date: String = cursor!!.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_DATE))
                val time: String = cursor!!.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_TIME))
                val memo: String = cursor!!.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TRANSACTION_MEMO))
                if (type == 1) {
                    totalSaved += amount
                } else if (type == 0){
                    totalSaved -= amount
                }
                if (date.equals(dateList.get(i))
                ) {
                    if (type == 1) {
                        result += amount
                    } else if (type == 0){
                        result -= amount
                    }
                    subItemList.add(SubItem(type, type, amount.toString() + " " + currency, time, memo))
                }
            }
            itemList.add(Item(getDayOfMonth(dateList.get(i)), getDayOfWeek(dateList.get(i)), getMonthAndYear(dateList.get(i)), result.toString(), subItemList))
            i++
            cursor!!.close()
        }
        db.close()
    }
}