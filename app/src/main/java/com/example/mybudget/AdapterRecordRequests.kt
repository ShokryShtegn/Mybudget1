package com.example.mybudget

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdapterRecordRequests(var context: Context, var recordList: ArrayList<Requests>): RecyclerView.Adapter<AdapterRecordRequests.HolderRecord>() {
        var nameUser: String = ""
        private var cont: Context = context
        private var recordList1: ArrayList<Requests> = recordList
        var storagePermissions = arrayOf<String?>() // only storage
        private final var STORAGE_REQUEST_CODE: Int = 101;
        var db = DataBaseHandler(context)
        class HolderRecord(requestView: View): RecyclerView.ViewHolder(requestView) {
            var userNameF = requestView.findViewById<TextView>(R.id.userNameFrom) as TextView
            var userEmailF = requestView.findViewById<TextView>(R.id.userEmailFrom) as TextView
            var datesended = requestView.findViewById<TextView>(R.id.date) as TextView
            var acceptR = requestView.findViewById<Button>(R.id.acceptRequest) as Button
            var dismissR = requestView.findViewById<Button>(R.id.dismissRequest) as Button
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRecordRequests.HolderRecord {
            // inflate layout
            var view: View = LayoutInflater.from(context).inflate(R.layout.confirm_request_page, parent, false)

            return HolderRecord(view)
        }

        override fun getItemCount(): Int {
            return recordList.size // return size of list/number of records
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: HolderRecord, position: Int) {
            // get data, set data, handle view clicks in this method


            Toast.makeText(context, "position: $position", Toast.LENGTH_SHORT).show()
            // get data
            var request: Requests = recordList[position]
            var id: Int = request.getId()
            var emailFrom: String = request.getEmailFrom()
            var emailTo: String = request.getEmailTo()
            var date: String = request.getDate()
            var acceptDate: String = request.getAcceptDate()

            GlobalVariable.requestId = id.toString()

            var dataUser = db.readData()
            for(i in 0..(dataUser.size - 1)){
                if(emailFrom.toString().equals(dataUser[i].email)){
                    nameUser = dataUser[i].userName
                }
            }
            // set data to views
            holder.userNameF.setText(nameUser)
            holder.userEmailF.setText(emailFrom.toString())
            holder.datesended.setText(date.toString())

            storagePermissions = arrayOf<String?>(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            var reqRead1 = db.readRequest(GlobalVariable.userIdA)
            for(j in 0 ..(reqRead1.size - 1)){
                if(holder.userEmailF.text.toString().equals(reqRead1[j].emailFrom)){
                    if(reqRead1[j].accept.equals("Yes", false)){
                        holder.acceptR.visibility = View.INVISIBLE
                    }
                }
            }
            holder.acceptR.setOnClickListener({
                var reqRead = db.readRequest(GlobalVariable.userIdA)
                for(i in 0 ..(reqRead.size - 1)){
                    if(holder.userEmailF.text.toString().equals(reqRead[i].emailFrom)){
                        val now = LocalDateTime.now()
                        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        var request = Requests(reqRead[i].getEmailFrom(), reqRead[i].getEmailTo(), reqRead[i].getDate(), formatter.format(now), "Yes", "", "", "")
                        db.updateRequest(request, reqRead[i].getId())
                        holder.acceptR.visibility = View.INVISIBLE
                    }
                }
            })
            holder.dismissR.setOnClickListener({
                Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show()
                var reqRead = db.readRequest(GlobalVariable.userIdA)
                for(i in 0 ..(reqRead.size - 1)){
                    if(holder.userEmailF.text.toString().equals(reqRead[i].emailFrom)){
                        val now = LocalDateTime.now()
                        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        var request = Requests(reqRead[i].getEmailFrom(), reqRead[i].getEmailTo(), reqRead[i].getDate(), reqRead[i].getAcceptDate(), "", "Yes", "", formatter.format(now))
                        db.updateRequest(request, reqRead[i].getId())
                        db.insertDismissReq(request, GlobalVariable.userIdA)
                        db.deleteRequest(reqRead[i].getId())

                    }
                }
            })
        }
}
