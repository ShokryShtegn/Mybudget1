package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.sign_dialog.*

class SignUpPopUp: AppCompatActivity() {
    var childName: String = ""
    private var signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.sign_dialog)
        // Set the Status bar appearance for different API levels

        saveChild.setOnClickListener({
            childName = userNameOfChild.text.toString()
            val b = Bundle()
            b.putString("childName", childName);
            signUpFragment.arguments = b;
            val intent = Intent(this, Main::class.java)
            intent.putExtra("childName", childName)
            startActivity(intent)
        })
    }
}