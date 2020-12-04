package com.example.mybudget

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mybudget.Global.Companion.USER_ID
import kotlinx.android.synthetic.main.fragment_login.*
import java.text.SimpleDateFormat
import java.util.*



class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }
    private var mIsShowPass = false

    @RequiresApi(Build.VERSION_CODES.N)
    var formatdate = SimpleDateFormat("dd MMMM YYYY", Locale.US)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_login, container, false)
        val context = (activity?.applicationContext ?: context) as Context
        val loginBtn = view.findViewById<Button>(R.id.btn_login) as Button
        val resetPasswordBtn = view.findViewById<Button>(R.id.sendEmail) as Button
        var showPass = view.findViewById<AppCompatImageView>(R.id.ivShowHidePass2)
        var db = DataBaseHandler(context)

        showPass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
        loginBtn.setOnClickListener({
            var data2 = db.readData()
            for(i in 0..(data2.size-1)) {
                if ((data2.get(i).email.toString().equals(emailField.text.toString())) && (data2.get(i).password.toString().equals(passField.text.toString()))){
                    if(emailField.text.toString().equals(GlobalVariable.userEmailToRelation)){
                        GlobalVariable.userEmailReq = GlobalVariable.userEmailToRelation
                        USER_ID = data2.get(i).id
                        val intent1 = Intent(context, homeLight::class.java)
                        intent1.putExtra("User_Email", data2[i].email)
                        startActivity(intent1)

                        val intent = Intent(getActivity(), RequestsRelations::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                        val intent_2 = Intent(getActivity(), ConfirmRequest::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        val pendingIntent_2: PendingIntent = PendingIntent.getActivity(context, 0, intent_2, 0)

                        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Request")
                            .setContentText("Press here to accept or dismiss")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .addAction(R.mipmap.ic_launcher_round, "Request",
                                pendingIntent_2)

                        with(NotificationManagerCompat.from(context)) {
                            // notificationId is a unique int for each notification that you must define
                            notify(0, builder.build())
                            createNotificationChannel()
                        }
                    }else{
                        USER_ID = data2.get(i).id
                        var readReq = db.readRequestsRecords(data2[i].id)
                        for(j in 0 ..(readReq.size - 1)) {
                            if (emailField.text.toString().equals(readReq[j].emailTo, false)) {
                                GlobalVariable.userEmailReq = readReq[j].emailTo
                                USER_ID = data2.get(i).id
                                break
                            }
                        }
                        val intent = Intent(context, homeLight::class.java)
                        intent.putExtra("User_Email", data2[i].email)
                        startActivity(intent)
                    }
                    break
                } else if(!(data2.get(i).email.toString().equals(emailField.text.toString())) && (data2.get(i).password.toString().equals(passField.text.toString()))) {
                    Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                }
            }
            var data3 = db.readDataSeller()
            for(i in 0..(data3.size-1)) {
                if ((data3.get(i).email.toString().equals(emailField.text.toString())) && (data3.get(i).password.toString().equals(passField.text.toString()))){
                    GlobalVariable.emailSeller = data3[i].email
                    val intent = Intent(context, SellerHomeLight::class.java)
                    intent.putExtra("User_Email", data3[i].email)
                    startActivity(intent)
                    break
                } else if(!(data3.get(i).email.toString().equals(emailField.text.toString())) && (data3.get(i).password.toString().equals(passField.text.toString()))) {
                    Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                }
            }
            //val intent = Intent(context, Home::class.java)
            //startActivity(intent)
        })
        resetPasswordBtn.setOnClickListener({
            val intent = Intent(context, SendEmailToResetPassword::class.java)
            startActivity(intent)
        })
        return view

    }
    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            // To show the password
            passField.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivShowHidePass2.setImageResource(R.drawable.ic_hide_login_password_24)
        } else {
            // To hide the password
            passField.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowHidePass2.setImageResource(R.drawable.ic_show_login_password_24)
        }
        // This line of code to put the pointer at the end of the password string
        passField.setSelection(passField.text.toString().length)
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}