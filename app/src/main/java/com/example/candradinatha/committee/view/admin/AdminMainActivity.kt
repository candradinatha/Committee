package com.example.candradinatha.committee.view.admin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.R.id.*
import com.example.candradinatha.committee.view.admin.approve.ApproveFragment
import com.example.candradinatha.committee.view.admin.committee.CommitteeFragment
import com.example.candradinatha.committee.view.admin.committee.add.AddCommitteeFragment
import com.example.candradinatha.committee.view.admin.profile.AdminProfileFragment
import com.example.candradinatha.committee.view.user.profile.UserProfileFragment
import kotlinx.android.synthetic.main.activity_admin_main.*

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                committee -> {
                    loadCommitteeFragment(savedInstanceState)
                }
                add_committee -> {
                    loadAddCommitteeFragment(savedInstanceState)
                }
                approve -> {
                    loadApproveFragment(savedInstanceState)
                }
                admin_profile -> {
                    loadAdminProfileFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = committee
    }

    private fun loadCommitteeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_main_container, CommitteeFragment(), CommitteeFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadAddCommitteeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_main_container, AddCommitteeFragment(), AddCommitteeFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadApproveFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_main_container, ApproveFragment(), ApproveFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadAdminProfileFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_main_container, AdminProfileFragment(), AdminProfileFragment::class.java.simpleName)
                    .commit()
        }
    }
}
