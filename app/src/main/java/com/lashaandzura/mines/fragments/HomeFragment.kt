package com.lashaandzura.mines.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lashaandzura.mines.GameActivity
import com.lashaandzura.mines.R
import com.lashaandzura.mines.adapters.ViewPagerAdapter

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var startButton: Button
    private lateinit var viewPager2: ViewPager2
    private lateinit var tableLayout: TabLayout
    val tabTitle = arrayOf("ჩვენ", "კიდევ ჩვენ")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.pager)
        tableLayout = view.findViewById(R.id.tableLayout)
        viewPager2.adapter = ViewPagerAdapter(activity!!.supportFragmentManager, lifecycle)

        TabLayoutMediator(tableLayout, viewPager2){
                tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        startButton = view.findViewById<Button>(R.id.startBtn)
        startButton.setOnClickListener {
            val intent = Intent(context, GameActivity::class.java)
            startActivity(intent)
        }


    }
}