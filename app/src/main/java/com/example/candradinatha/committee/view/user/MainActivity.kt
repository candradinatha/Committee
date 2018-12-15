package com.example.candradinatha.committee.view.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.R.id.*
import com.example.candradinatha.committee.view.user.mycommittee.MyCommitteeFragment
import com.example.candradinatha.committee.view.user.committee.UserCommitteeFragment
import com.example.candradinatha.committee.view.user.notif.UserNotificationFragment
import com.example.candradinatha.committee.view.user.profile.UserProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                user_committee -> {
                    loadUserFragment(savedInstanceState)
                }
                user_my_committee -> {
                    loadMyCommitteeFragment(savedInstanceState)
                }
                notification -> {
                    loadNotificationFragment(savedInstanceState)
                }
                user_profile -> {
                    loadUserProfileFragment(savedInstanceState)
                }
            }
            true
        }
        user_bottom_navigation.selectedItemId = user_committee
    }

    private fun loadUserFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.user_main_container, UserCommitteeFragment(), UserCommitteeFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadMyCommitteeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.user_main_container, MyCommitteeFragment(), MyCommitteeFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadNotificationFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.user_main_container, UserNotificationFragment(), UserNotificationFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadUserProfileFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.user_main_container, UserProfileFragment(), UserProfileFragment::class.java.simpleName)
                    .commit()
        }
    }
}


