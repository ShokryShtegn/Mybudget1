package com.example.mybudget

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget.Adapters.ItemAdapter
import com.example.mybudget.Adapters.SubItemAdapter
import com.example.mybudget.models.Item
import com.example.mybudget.models.SubItem
import kotlinx.android.synthetic.main.activity_ongoing_goal_info.*
import java.util.ArrayList

class AchieveGoalInfo : AppCompatActivity() {

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
        setContentView(R.layout.activity_achieve_goal_info)

        val mName = findViewById<TextView>(R.id.name)
        val mSaved = findViewById<TextView>(R.id.saved_value)
        val mRemain = findViewById<TextView>(R.id.remain_value)
        val mAmount = findViewById<TextView>(R.id.amount_value)
        val mTargetDate = findViewById<TextView>(R.id.target_date)

        readFromLocalStorage()

        val rvItem: RecyclerView = findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this@AchieveGoalInfo)
        val itemAdapter = ItemAdapter(itemList)
        rvItem.setAdapter(itemAdapter)
        rvItem.setLayoutManager(layoutManager)

        val disabler: RecyclerView.OnItemTouchListener = RecyclerViewDisabler()
        //rvItem.addOnItemTouchListener(disabler)

        progres_bar.max = 100
        val percent = (Global.GOAL_AMOUNT - totalSaved)/100.0
        progres_bar.progress = percent.toInt()

        toolbar.inflateMenu(R.menu.menu4)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@AchieveGoalInfo, Goal::class.java)
            startActivity(intent)
        }

        mName.setText(Global.GOAL_NAME)
        mSaved.setText(totalSaved.toString())
        mRemain.setText((Global.GOAL_AMOUNT - totalSaved).toString())
        mAmount.setText(Global.GOAL_AMOUNT.toString() + " " + Global.GOAL_CURRENCY)
        mTargetDate.setText(Global.GOAL_TARGET_DATE)

        listener = object : SubItemAdapter.SubRecyclerViewClickListener {
            override fun onRowClick(view: View?, position: Int) {
                val intent = Intent(this@AchieveGoalInfo, AddGoalTransactin::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.ic_edit -> {
                Global.GOAL = false
                Toast.makeText(this, Global.GOAL_ID.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AchieveGoalInfo, AddGoal::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readFromLocalStorage() {
        val context = this@AchieveGoalInfo
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.readableDatabase
        var cursor: Cursor? =  dataBaseHandler.getGoalTransactionGroupByDate(Global.GOAL_ID)
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
            cursor =  dataBaseHandler.getGoalTransaction(Global.GOAL_ID)
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
            itemList.add(Item(Global.getDayOfMonth(dateList.get(i)), Global.getDayOfWeek(dateList.get(i)), Global.getMonthAndYear(dateList.get(i)), result.toString(), subItemList))
            i++
            cursor!!.close()
        }
        db.close()
    }
}