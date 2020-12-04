package com.example.mybudget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.emailField
import kotlinx.android.synthetic.main.fragment_login.passField
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.reset_password.*

class ResetPassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_password)
        var intent = getIntent()
        var emailUser = intent.getStringExtra("emailUser")
        supportActionBar?.title = emailUser
        Toast.makeText(this, emailUser.toString(), Toast.LENGTH_SHORT).show();
        var context: Context = this
        reset.setOnClickListener({
            var db = DataBaseHandler(context)
            var data2 = db.readData()
            for(i in 0..(data2.size-1)) {
                if ((data2.get(i).email.toString().equals(emailUser.toString(), false)) && (newPassword.text.toString().trim().equals(verifyNewPassword.text.toString().trim(), false))){
                    var userName = data2.get(i).userName.toString()
                    var emailUs = data2.get(i).email.toString()
                    var passwordUs = newPassword.text.toString().trim()
                    var userType = data2[i].userType
                    var age = data2[i].age
                    var image = data2[i].image
                    var relation = data2[i].relation
                    var relationEmail = data2[i].relationEmail
                    var user = User(
                        userName,
                        passwordUs,
                        emailUs,
                        userType,
                        age,
                        image,
                        relation,
                        relationEmail
                    )
                    db.updateData(user)
                    Toast.makeText(context, "Password updated.", Toast.LENGTH_SHORT).show()
                    break
                } else if(!(data2.get(i).email.toString().equals(emailUser.toString()))) {
                    Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                }
            }
            var data3 = db.readDataSeller()
            for(i in 0..(data3.size-1)) {
                if ((data3.get(i).email.toString().equals(emailUser.toString(), false)) && (newPassword.text.toString().trim().equals(verifyNewPassword.text.toString().trim(), false))){
                    var userName = data3.get(i).userName.toString()
                    var emailUs = data3.get(i).email.toString()
                    var passwordUs = newPassword.text.toString().trim()
                    var age = data3[i].age
                    var image = data3[i].image
                    var seller = Sellers(
                        userName,
                        passwordUs,
                        emailUs,
                        age,
                        image
                    )
                    db.updateDataSeller(seller)
                    Toast.makeText(context, "Password updated.", Toast.LENGTH_SHORT).show()
                    break
                } else if(!(data2.get(i).email.toString().equals(emailUser.toString()))) {
                    Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                }
            }
        })
        backToLogin.setOnClickListener({
            val intent = Intent(context, Main::class.java)
            startActivity(intent)
        })
    }
}