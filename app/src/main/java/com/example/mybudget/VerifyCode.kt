package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.send_email.*
import kotlinx.android.synthetic.main.verify_code.*

class VerifyCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_code)

        verify.setOnClickListener({
            var codeNumber = verifyCode.text.toString()
            var intent = getIntent()
            var randomNum = intent.getStringExtra("randomNumber")
            supportActionBar?.title = randomNum
            var emailUser = intent.getStringExtra("etRecipient")
            supportActionBar?.title = emailUser
            if (codeNumber.toString().trim().equals(randomNum.toString().trim(), false)) {
                val intent = Intent(this, ResetPassword::class.java)
                intent.putExtra("emailUser", emailUser.toString())
                startActivity(intent)
            }
        })
    }
}


