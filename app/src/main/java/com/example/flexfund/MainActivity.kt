package com.example.flexfund

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flexfund.databinding.ActivityMainBinding
import com.example.flexfund.ui.home.HomeFragment
import com.example.flexfund.ui.goals.GoalsFragment
import com.example.flexfund.ui.insights.InsightsFragment
import com.example.flexfund.ui.transactions.TransactionsFragment
import com.example.flexfund.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_transactions -> replaceFragment(TransactionsFragment())
                R.id.nav_goals -> replaceFragment(GoalsFragment())
                R.id.nav_insights -> replaceFragment(InsightsFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}