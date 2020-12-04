package com.example.mybudget.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
   private val myFragmentList = ArrayList<Fragment>()
    private val myFragmentTitelList = ArrayList<String>()

    override fun getCount(): Int {
        return myFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return myFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return myFragmentTitelList[position]
    }
    fun addFragment(fragment: Fragment, title: String){
        myFragmentList.add(fragment)
        myFragmentTitelList.add(title)
    }

}