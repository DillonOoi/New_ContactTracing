package com.example.new_contacttracing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.new_contacttracing.history.HistoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED VARIABLE") // for login and register

        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.statistics -> {
                    loadFragment(StatisticsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.check_in -> {
                    loadFragment(CheckInFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.history -> {
                    loadFragment(HistoryFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.notifications -> {
                    loadFragment(NotificationsFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}