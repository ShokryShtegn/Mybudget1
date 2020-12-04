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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.mybudget.*
import com.example.mybudget.Adapters.AchieveGoalAdapter
import com.example.mybudget.Global.Companion.USER_ID
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


/**
 * created by Isra
 */
class AchieveGoalFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: AchieveGoalAdapter? = null
    private val goalsList: MutableList<com.example.mybudget.models.Goal> = ArrayList()
    var listener: AchieveGoalAdapter.RecyclerViewClickListener? = null

    //1 means data is synced and 0 means data is not synced
    val NAME_SYNCED_WITH_SERVER = 1
    val NAME_NOT_SYNCED_WITH_SERVER = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_achieve_goal, container, false)


        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView


        val context = activity as Context
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager

        listener = object : AchieveGoalAdapter.RecyclerViewClickListener {
            override fun onRowClick(view: View?, position: Int) {
                Global.GOAL_ID = goalsList[position].getId()
                Global.GOAL_USER = goalsList[position].getUser()
                Global.GOAL_WALLET = goalsList[position].getWallet()
                Global.GOAL_NAME = goalsList[position].getName()
                Global.GOAL_AMOUNT = goalsList[position].getAmount()
                Global.GOAL_CURRENCY = goalsList[position].getCurrency()!!
                Global.GOAL_TARGET_DATE = goalsList[position].getTargetDate()!!
                Global.GOAL_COLOR = goalsList[position].getColor()
                Global.GOAL_ACHIEVE = goalsList[position].getAchieve()
                Global.GOAL_STARRED = goalsList[position].getStarred()
                Global.GOAL_STATUS = goalsList[position].getStatus()
                val intent = Intent(context, AchieveGoalInfo::class.java)
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

            if(achieve ==  1){
                goalsList.add(com.example.mybudget.models.Goal(id, user, wallet, name, amount, currency, targetDate, color, achieve, starred, status))
            }
        }
        adapter?.notifyDataSetChanged()
        cursor.close()
        db.close()
        adapter = AchieveGoalAdapter(goalsList, context, listener!!)
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

