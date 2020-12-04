package com.example.mybudget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import kotlinx.android.synthetic.main.sign_dialog.*
import kotlinx.android.synthetic.main.sign_dialog_child.*
import kotlinx.android.synthetic.main.wallet_dialog.*

class SignUpPopUpChild: AppCompatActivity() {
    var parName: String = ""
    private var signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mybudget.R.layout.sign_dialog_child)

        saveAge.setOnClickListener({
            parName = parentName.toString()
            val b = Bundle()
            b.putString("parName", parName);
            signUpFragment.arguments = b;
            val intentBackToSignFromChild = Intent(this, Main::class.java)
            intent.putExtra("parName", parName)
            startActivity(intentBackToSignFromChild)
        })
    }
}