package com.example.candradinatha.committee.view.admin.committee


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.CommitteePagerAdapter
import kotlinx.android.synthetic.main.fragment_committee.*

class CommitteeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentAdapter = CommitteePagerAdapter(fragmentManager)
        committee_view_pager.adapter = fragmentAdapter

        committee_tab.setupWithViewPager(committee_view_pager)
    }


}
