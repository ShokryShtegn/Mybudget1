package com.example.mybudget

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {return LoginFragment()}
            1 -> {return SignUpFragment()}
            else -> {return LoginFragment()}
        }

    }
    override fun getPageTitle(position: Int): CharSequence?{
        when(position){
            0 -> {return "Login"}
            1 -> {return "Sign up"}
        }
        return super.getPageTitle(position)
    }
    override fun getCount(): Int {
        return 2
    }
}