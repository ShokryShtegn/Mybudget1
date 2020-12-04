package com.example.mybudget.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mybudget.fragment.AchieveGoalFragment
import com.example.mybudget.fragment.PayableDebtFragment
import com.example.mybudget.fragment.ReceivableDebtFragment

@Suppress("DEPRECATION")
internal class DebtAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PayableDebtFragment()
            }
            1 -> {
                ReceivableDebtFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}