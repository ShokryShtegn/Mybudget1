package com.example.mybudget

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.example.mybudget.Adapters.DebtAdapter
import com.example.mybudget.Adapters.GoalAdapter
import com.example.mybudget.Global.Companion.initDebt
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_add_goal.*

class Debt : AppCompatActivity() {lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt)

        val context = this as Context
        title = "Debt"
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewpager)
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = DebtAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@Debt, homeLight::class.java)
            intent.putExtra("Destination", "Manage")
            startActivity(intent)
        }

        val add_goal_btn = findViewById<FloatingActionButton>(R.id.add) as FloatingActionButton
        add_goal_btn.setOnClickListener({
            initDebt()
            val intent = Intent(context, AddDebt::class.java)
            startActivity(intent)
        })
    }
}