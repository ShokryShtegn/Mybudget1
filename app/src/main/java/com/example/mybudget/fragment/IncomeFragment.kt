package com.example.mybudget.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.mybudget.DataBaseHandler
import com.example.mybudget.GlobalVariable
import com.example.mybudget.R
import com.example.mybudget.homeLight
import com.example.mybudget.tabel.TransactionTable
import kotlinx.android.synthetic.main.fragment_income.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class IncomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_income, container, false)
        val context = activity as Context
        val db = DataBaseHandler(context)
        val Category_Income = view.findViewById<Spinner>(R.id.spinner_Cat_Inc) as Spinner
        val Amount_Income = view.findViewById<EditText>(R.id.amount_Income) as EditText
        val Note_Income = view.findViewById<EditText>(R.id.note_Income) as EditText
        val Date_Income = view.findViewById<EditText>(R.id.date_Income) as EditText
        val Wallet_Income = view.findViewById<Spinner>(R.id.spinner_Wallet_Inc) as Spinner
        val SaveButton = view.findViewById<Button>(R.id.save_Income_Transaction) as Button
        val backButton = view.findViewById<Button>(R.id.back) as Button
        var result1: String = ""
        var result2: String = ""
        var ID_OF_USER = GlobalVariable.user_id

        val Incomes = resources.getStringArray(R.array.Incomes)
        Category_Income.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            Incomes
        )
        Category_Income.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result1 = Category_Income.selectedItem.toString()
                Toast.makeText(context, "Category is: " + result1, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }

        val arrayWalletName = ArrayList<String>()
        var dU = db.readData()
        for (j in 0..(dU.size - 1)) {
            if (dU[j].id == ID_OF_USER) {
                var data1 = db.readWallet(ID_OF_USER)
                if (data1.size == 0) {
                    Toast.makeText(context, "No Wallets to this user: " + ID_OF_USER.toString(), Toast.LENGTH_SHORT).show()
                }
                else
                    for (i in 0..(data1.size - 1)) {
                        for (j in 0..(arrayWalletName.size - 1)) {
                            if (arrayWalletName[j] != data1[i].wallet_name) {
                                arrayWalletName.add(data1[i].wallet_name)
                                break
                            }
                        }
                    }

            }
        }


        Wallet_Income.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            arrayWalletName
        )
        Wallet_Income.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result2 = Wallet_Income.selectedItem.toString()
                Toast.makeText(context, "Wallet Name is: " + result2, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }


        Date_Income.setOnClickListener({
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                context, DatePickerDialog.OnDateSetListener
                { view, year, monthOfYear, dayOfMonth ->

                    Date_Income.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                }, year, month, day
            )
            dpd.show()
        })



        SaveButton.setOnClickListener({

            var dU = db.readData()
            for (j in 0..(dU.size - 1)) {
                if (dU[j].id == ID_OF_USER) {
                    var data1 = db.readTransaction(ID_OF_USER)
                    if (data1.size == 0) {
                        if (Category_Income.isSelected
                            && Amount_Income.text.isNotEmpty()
                            && Date_Income.text.isNotEmpty()
                            && Wallet_Income.isSelected
                        ) {
                            var transaction1 = TransactionTable(
                                "Incomes" ,
                                result1,
                                Amount_Income.text.toString().toInt(),
                                Note_Income.text.toString(),
                                Date_Income.text.toString(),
                                result2
                            )

                            db.insertTransaction(transaction1, ID_OF_USER)


                            Amount_Income.setText(null)
                            Note_Income.setText(null)
                            Date_Income.setText(null)

                            Log.d("Transaction", "Selected")
                            break
                        } else if ( Amount_Income.text.isEmpty()
                            || Date_Income.text.isEmpty()
                        ) {

                            Toast.makeText(
                                context, "Please fill All Data's",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        for (i in 0..(data1.size - 1)) {
                            if (Category_Income.isSelected
                                && Amount_Income.text.isNotEmpty()
                                && Date_Income.text.isNotEmpty()
                                && Wallet_Income.isSelected
                            ) {

                                var transaction2 = TransactionTable(
                                    "Incomes",
                                    result1,
                                    Amount_Income.text.toString().toInt(),
                                    Note_Income.text.toString(),
                                    Date_Income.text.toString(),
                                    result2
                                )

                                db.insertTransaction(transaction2, ID_OF_USER)


                                Amount_Income.setText(null)
                                Note_Income.setText(null)
                                Date_Income.setText(null)

                                Log.d("Transaction", "Selected")
                                break

                            } else if (Amount_Income.text.isEmpty()
                                || date_Income.text.isEmpty()
                            ) {
                                Toast.makeText(
                                    context, "Please fill All Data's",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }//end of the current user
            }//End for loop to all user
        })
        backButton.setOnClickListener({
            val intent = Intent(context, homeLight::class.java)
            startActivity(intent)
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment incomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IncomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}