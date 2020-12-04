package com.example.mybudget

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.mybudget.fragments.FutureFragment
import com.example.mybudget.fragments.ThisMonthFragment
import com.example.mybudget.fragments.LastMonthFragment
import com.example.mybudget.fragments.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.income_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*


class Home : AppCompatActivity() {
    lateinit var toolbar: ActionBar
    private var content: FrameLayout? = null
    var formatdate = SimpleDateFormat("dd MMMM YYYY", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setUpTabs()
        bottomnav()
        createincomedialog()
        btn_List.setOnClickListener({
            val popupMenu = PopupMenu(this,it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId){
                    //R.id.Search_For_Transaction ->true
                    //R.id.Currency_Converter -> true
                    //R.id.Advice_System ->true
                    //R.id.Map ->true
                    //R.id.Backup ->true
                    else -> false
                }
            }
            //popupMenu.inflate(R.menu.list_menu)
            popupMenu.show()
            /* try {
                 val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
                 fieldMpopup.isAccessible = true
                 val mPopup = fieldMpopup.get(popupMenu)
                 mPopup.javaClass
                     .getDeclaredMethod("setForceShoeIcon", Boolean::class.java)
                     .invoke(mPopup,true)
             }catch (e:Exception){
                 Log.e("Main","Error showing menu icons.",e)
             }finally {
                 popupMenu.show()
             }*/
        })
    }
    private fun tabs(count : Int) : ArrayList<String> {
        //val count = 10
        val cal = Calendar.getInstance()
        var month = cal[Calendar.MONTH] + 1
        var year = cal[Calendar.YEAR]
        var _month = ""

        val list = ArrayList<String>()

        var x = 0
        while (x < count) {
            when (month) {
                1  -> _month = "JAN"
                2  -> _month = "FEB"
                3  -> _month = "MAR"
                4  -> _month = "APR"
                5  -> _month = "MAY"
                6  -> _month = "JUN"
                7  -> _month = "JUL"
                8  -> _month = "AUG"
                9  -> _month = "SEP"
                10 -> _month = "OCT"
                11 -> _month = "NOV"
                12 -> _month = "DEC"
            }
            if (_month == "DEC")
                year--;

            list.add(_month + " " + year.toString())
            //println(list[x])
            x++
            month--;
        }
        return list
    }

    private fun bottomnav(){
        //val accountfrag = AccountFragment()
        //val calenderfrag = CalenderFragment()
        // val addfrag = AddFragment()
        //val transactionfrag = TrancactionFragment()
        btn_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                //R.id.account ->makeCurrentFragment(accountfrag)
                //R.id.calender ->makeCurrentFragment(calenderfrag)
                //R.id.add ->makeCurrentFragment(addfrag)
                //R.id.charts ->makeCurrentFragment(chartsfrag)
                //R.id.transaction ->makeCurrentFragment(transactionfrag)
            }
            false
        }
    }


    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
                .commit()
        }
    }


    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        var x = 0
        var count = 10
        val tab : ArrayList<String> = tabs(count)

        var i : String = ""

        // Needs to be modified, tabs should be added dynamically
        for (i in tab) {
            if (x == 0){
                adapter.addFragment(FutureFragment(), i)
            }
            else if (x == 1){
                adapter.addFragment(ThisMonthFragment(), i)
            }
            else
                adapter.addFragment(LastMonthFragment(), i)
            x++
        }
        viewPagerhome.adapter = adapter
        tabs.setupWithViewPager(viewPagerhome)

        x = 0
        while (x < count){
            tabs.getTabAt(x)
            x++
        }
    }


    fun createincomedialog(){
        val myDialogView = LayoutInflater.from(this).inflate(R.layout.income_dialog, null);
        val myBuilder = AlertDialog.Builder(this).setView(myDialogView)
        val myAlertDialog = myBuilder.show()
        myDialogView.from_date.setOnClickListener({
            val c: Calendar = Calendar.getInstance()
            val myDatePicker =
                DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                        val selectDate = Calendar.getInstance()
                        selectDate.set(Calendar.YEAR, i)
                        selectDate.set(Calendar.MONTH, i2)
                        selectDate.set(Calendar.DAY_OF_YEAR, i3)
                        val date = formatdate.format(selectDate.time).toString()
                        myDialogView.from_date.setText(date)
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
                )
        })
        myDialogView.to_date.setOnClickListener({
            val c: Calendar = Calendar.getInstance()
            val myDatePicker =
                DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                        val selectDate = Calendar.getInstance()
                        selectDate.set(Calendar.YEAR, i)
                        selectDate.set(Calendar.MONTH, i2)
                        selectDate.set(Calendar.DAY_OF_YEAR, i3)
                        val date: String = formatdate.format(selectDate.time)
                        myDialogView.to_date.setText(date)
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
                )
        })
        myDialogView.btn_done.setOnClickListener({
            if ((myDialogView.income_value.text.toString().isNotEmpty()) &&
                (myDialogView.saving_value.text.toString().isNotEmpty()) &&
                (myDialogView.from_date.text.toString().isNotEmpty()) &&
                (myDialogView.to_date.text.toString().isNotEmpty()) &&
                (myDialogView.from_date.text.toString()
                    .equals(myDialogView.to_date.text.toString(), true))) {
                true
            }
        })
        myDialogView.btn_cancel.setOnClickListener({
            myAlertDialog.dismiss()
        })
    }
}








