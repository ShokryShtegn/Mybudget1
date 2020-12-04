package com.example.mybudget.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mybudget.fragment.AchieveGoalFragment
import com.example.mybudget.fragment.OngoingGoalFragment

@Suppress("DEPRECATION")
internal class GoalAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OngoingGoalFragment()
            }
            1 -> {
                AchieveGoalFragment()
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}