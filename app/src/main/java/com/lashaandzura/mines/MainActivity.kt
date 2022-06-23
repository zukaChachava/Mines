package com.lashaandzura.mines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lashaandzura.mines.adapters.ViewPagerAdapter
import com.lashaandzura.mines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var viewPager2: ViewPager2
    private lateinit var tableLayout: TabLayout
    val tabTitle = arrayOf("Zura", "Lasha")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // Auto binding, FindViewById is no more needed
        setContentView(binding.root)
        viewPager2 = binding.pager
        tableLayout = binding.tableLayout
        bottomNavigationView = binding.bottomNavView
        navController = findNavController(R.id.navHostFragment)
        viewPager2.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tableLayout, viewPager2){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        setupActionBarWithNavController(navController, AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.statisticFragment
            )
        )
        )
        bottomNavigationView.setupWithNavController(navController)
    }

}