package com.example.mybudget.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.mybudget.*
import com.example.mybudget.Map
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.ArrayList

class TransactionFragment : Fragment(){
    var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //(activity as AppCompatActivity).supportActionBar
        //setSupportActionBar(findViewById(R.id.toolbar))
    }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu1, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        val context = activity as Context
        when (item.itemId) {
            R.id.ic_transaction -> {
                Toast.makeText(context, "Transaction Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Transaction::class.java)
                startActivity(intent)
                return true
            }

            R.id.ic_search -> {
                Toast.makeText(context, "Search Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SearchForTransaction::class.java)
                startActivity(intent)
                return true
            }

            R.id.transfer_money -> {
                Toast.makeText(context, "Transfer Money Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, TransferMoney::class.java)
                startActivity(intent)
                return true
            }
            R.id.currency_converyer -> {
                Toast.makeText(
                    context,
                    "Currency Converter Is Selected Is Selected",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(context, CurrencyConverter::class.java)
                startActivity(intent)
                return true
            }
            R.id.tip -> {
                Toast.makeText(context, "Advice System Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdviceSystem::class.java)
                startActivity(intent)
                return true
            }
            R.id.map -> {
                Toast.makeText(context, "Map Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Map::class.java)
                startActivity(intent)
                return true
            }
            R.id.back_up -> {
                Toast.makeText(context, "Back Up Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Backup::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val bundle = arguments
        val UserIDLog = bundle?.getString("User_id")
        Toast.makeText(context, UserIDLog.toString(), Toast.LENGTH_SHORT).show()
        val view: View = inflater.inflate(R.layout.fragment_transaction, container, false)
        val context = activity as Context
        val goal_btn = view.findViewById<FloatingActionButton>(R.id.addTransactionButton) as FloatingActionButton
        //val user_spinner = view.findViewById<Spinner>(R.id.card_type) as Spinner

        val add = view.findViewById<FloatingActionButton>(R.id.addTransactionButton) as FloatingActionButton
        var db = DataBaseHandler(context)
        var users = db.readData()
        val list = ArrayList<String>()
        // Get Uses' Names
        for(i in 0..(users.size-1)) {
            list.add(users.get(i).userName.toString())
        }
        list.add("+")
        //(activity as AppCompatActivity).supportActionBar
        //setSupportActionBar(toolbar)

        /*toolbar.inflateMenu(R.menu.menu1)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }*/

        // target date spinner on click
        goal_btn.setOnClickListener({
            //val intent = Intent(context, Goal::class.java)
            //startActivity(intent)
        })
        add.setOnClickListener({
            val intent = Intent(context, AddTransaction::class.java)
            startActivity(intent)
        })
        // test spinner
        // The spinners' items must be the usernames who are registered on the application on that device

        // val names = arrayOf("Isra 1", "Isra 2")

        /*user_spinner.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            list
        )

        user_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result = user_spinner.selectedItem.toString()
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                /*if(result.equals("+", false)){
                    user_spinner.setOnItemClickListener(){
                        val intent = Intent(context, Wallet::class.java)
                        startActivity(intent)
                    }
                }*/
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }*/

        //setHasOptionsMenu(true)
        return view
    }

    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val context = activity as Context
        when (item.itemId) {
            R.id.ic_transaction -> {
                Toast.makeText(context, "Transaction Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Transaction::class.java)
                startActivity(intent)
                return true
            }

            R.id.ic_search -> {
                Toast.makeText(context, "Search Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SearchForTransaction::class.java)
                startActivity(intent)
                return true
            }

            R.id.transfer_money -> {
                Toast.makeText(context, "Transfer Money Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, TransferMoney::class.java)
                startActivity(intent)
                return true
            }
            R.id.currency_converyer -> {
                Toast.makeText(
                    context,
                    "Currency Converter Is Selected Is Selected",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(context, CurrencyConverter::class.java)
                startActivity(intent)
                return true
            }
            R.id.tip -> {
                Toast.makeText(context, "Advice System Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, AdviceSystem::class.java)
                startActivity(intent)
                return true
            }
            R.id.map -> {
                Toast.makeText(context, "Map Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Map::class.java)
                startActivity(intent)
                return true
            }
            R.id.back_up -> {
                Toast.makeText(context, "Back Up Is Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Backup::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/
}