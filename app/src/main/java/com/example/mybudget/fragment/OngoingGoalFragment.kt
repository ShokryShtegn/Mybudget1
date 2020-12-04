package com.example.mybudget.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.mybudget.*
import com.example.mybudget.Adapters.OngoingGoalAdapter
import com.example.mybudget.Adapters.OngoingGoalAdapter.RecyclerViewClickListener
import com.example.mybudget.Global.Companion.GOAL_ACHIEVE
import com.example.mybudget.Global.Companion.GOAL_AMOUNT
import com.example.mybudget.Global.Companion.GOAL_COLOR
import com.example.mybudget.Global.Companion.GOAL_CURRENCY
import com.example.mybudget.Global.Companion.GOAL_ID
import com.example.mybudget.Global.Companion.GOAL_NAME
import com.example.mybudget.Global.Companion.GOAL_STARRED
import com.example.mybudget.Global.Companion.GOAL_STATUS
import com.example.mybudget.Global.Companion.GOAL_TARGET_DATE
import com.example.mybudget.Global.Companion.GOAL_USER
import com.example.mybudget.Global.Companion.GOAL_WALLET
import com.example.mybudget.Global.Companion.USER_ID
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

/*
 * created by Isra
 */

class OngoingGoalFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: OngoingGoalAdapter? = null
    private val goalsList: MutableList<com.example.mybudget.models.Goal> = ArrayList()
    var listener: RecyclerViewClickListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_ongoing_goal, container, false)


        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView


        val context = activity as Context
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager

        listener = object : RecyclerViewClickListener {
            override fun onRowClick(view: View?, position: Int) {
                GOAL_ID = goalsList[position].getId()
                GOAL_USER = goalsList[position].getUser()
                GOAL_WALLET = goalsList[position].getWallet()
                GOAL_NAME = goalsList[position].getName()
                GOAL_AMOUNT = goalsList[position].getAmount()
                GOAL_CURRENCY = goalsList[position].getCurrency()!!
                GOAL_TARGET_DATE = goalsList[position].getTargetDate()!!
                GOAL_COLOR = goalsList[position].getColor()
                GOAL_ACHIEVE = goalsList[position].getAchieve()
                GOAL_STARRED = goalsList[position].getStarred()
                GOAL_STATUS = goalsList[position].getStatus()
                val intent = Intent(context, OngoingGoalInfo::class.java)
                startActivity(intent)
            }

            override fun onStarClick(view: View?, position: Int) {
                val id: Int = goalsList.get(position).getId()
                val star: Int = goalsList[position].getStarred()
                val mStar = view?.findViewById<ImageView>(R.id.star) as ImageView
                if (star == 1) {
                    mStar.setImageResource(R.drawable.starof)
                    goalsList[position].setStarred(0)
                    updateStar(id, 0)
                    adapter?.notifyDataSetChanged()
                } else {
                    mStar.setImageResource(R.drawable.staron)
                    goalsList[position].setStarred(1)
                    updateStar(id, 1)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
        readFromLocalStorage()
        return view
    }

    fun readFromLocalStorage() {
        val context = activity as Context
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.readableDatabase
        val cursor: Cursor? =  dataBaseHandler.getGoals(USER_ID)
        goalsList.clear()
        while(cursor?.moveToNext()!!){
            val id: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_ID))
            val user: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COl_GOAL_USER_ID))
            val wallet: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COl_GOAL_WALLET_ID))
            val name: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_NAME))
            val amount: Double = cursor.getDouble(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_AMOUNT))
            val currency: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_CURRENCY))
            val targetDate: String = cursor.getString(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_TARGET_DATE))
            val color: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_COLOR))
            val achieve: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_ACHIEVE))
            val starred: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_STARRED))
            val status: Int = cursor.getInt(cursor.getColumnIndex(dataBaseHandler.COL_GOAL_STATUS))

            if(achieve ==  0){
                goalsList.add(com.example.mybudget.models.Goal(id, user, wallet, name, amount, currency, targetDate, color, achieve, starred, status))
            }
        }
        adapter?.notifyDataSetChanged()
        cursor.close()
        db.close()
        adapter = OngoingGoalAdapter(goalsList, context, listener!!)
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    private fun updateStar(id: Int, updateTo: Int) {
        updateOnAppServer(id, updateTo)
    }

    private fun updateOnAppServer(
        id: Int, starred: Int
    ) {
        val context = activity as Context
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Updating Goal...")
        progressDialog.show()
        val stringRequest: StringRequest =
            object : StringRequest(
                Request.Method.POST, Global.URL_UPDATE_GOAL_IS_STARRED,
                object : Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        progressDialog.dismiss()
                        try {
                            val obj = JSONObject(response)
                            if (!obj.getBoolean("error")) {
                                updateOnLocalStorage(
                                    id,
                                    starred,
                                    Global.SYNCED_WITH_SERVER
                                )
                                Toast.makeText(context, "ERROR FALSE", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                updateOnLocalStorage(
                                    id,
                                    starred,
                                    Global.NOT_SYNCED_WITH_SERVER
                                )
                                Toast.makeText(context, "ERROR TRUE", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        progressDialog.dismiss()
                        //on error storing the name to sqlite with status unsynced
                        updateOnLocalStorage(
                            id,
                            starred,
                            Global.NOT_SYNCED_WITH_SERVER
                        )
                    }
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params.put("id", id.toString())
                    params.put("starred", starred.toString())
                    return params
                }
            }
        VolleySingleton.getInstance(context)!!.addToRequestQueue(stringRequest)
    }

    private fun updateOnLocalStorage(
        id: Int, star: Int, status: Int
    ) {
        val context = activity as Context
        val dataBaseHandler: DataBaseHandler = DataBaseHandler(context)
        val db: SQLiteDatabase = dataBaseHandler.writableDatabase
        dataBaseHandler.updateGoalIsStarred(id, star, status)
        db.close()
    }

}

