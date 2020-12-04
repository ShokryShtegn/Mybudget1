package com.example.mybudget.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mybudget.DataBaseHandler
import com.example.mybudget.GlobalVariable
import com.example.mybudget.R
import kotlinx.android.synthetic.main.add_transaction.*



class AddTransaction: AppCompatActivity() {
    val incomefragment = IncomeFragment()
    val expensesfragment = ExpensesFragment()
    var context: Context = this
    val db = DataBaseHandler(context)
    var ID_Of_User: String = ""
    var uuid: String = ""




    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_transaction)
        replaceFragments(incomefragment)

        btn_nav_transaction.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Income ->
                    replaceFragments(incomefragment)

                R.id.Expenses ->
                    replaceFragments(expensesfragment)

                else -> false
            }
            true


        }


        var intentS = getIntent()
        ID_Of_User = intentS.getStringExtra("User_id").toString()
        supportActionBar?.title = ID_Of_User
        Toast.makeText(context, "ID Of User In begin: " + ID_Of_User, Toast.LENGTH_SHORT).show()

    }
    private fun replaceFragments(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_transaction, fragment)
            transaction.commit()
        }

    }
}
