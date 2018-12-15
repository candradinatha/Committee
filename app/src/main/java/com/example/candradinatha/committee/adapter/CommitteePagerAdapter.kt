package com.example.candradinatha.committee.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteeFragment
import com.example.candradinatha.committee.view.admin.committee.prev.PrevCommitteeFragment

class CommitteePagerAdapter(fm: FragmentManager?): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PrevCommitteeFragment()
            }
            else -> {
                NextCommitteeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return  when (position) {
            0 -> "Non Aktif"
            else -> {
                return "Aktif"
            }
        }
    }
}