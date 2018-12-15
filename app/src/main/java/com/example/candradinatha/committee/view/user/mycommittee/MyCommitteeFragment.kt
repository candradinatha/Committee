package com.example.candradinatha.committee.view.user.mycommittee


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.MyCommitteePagerAdapter
import kotlinx.android.synthetic.main.fragment_my_committee.*

class MyCommitteeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentManager = MyCommitteePagerAdapter(fragmentManager)
        my_committee_view_pager.adapter = fragmentManager

        my_committee_tab.setupWithViewPager(my_committee_view_pager)
    }
}
