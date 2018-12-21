package com.example.candradinatha.committee.view.user.mycommittee


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.candradinatha.committee.App

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.MyCommitteeAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Kepanitiaan
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.user.committee.DetailCommitteeUserActivity
import kotlinx.android.synthetic.main.fragment_my_current_committee.*
import kotlinx.android.synthetic.main.fragment_my_previous_committee.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class MyPreviousCommitteeFragment : Fragment(), MyCommiteeContract.View {

    private var kepanitiaanku: MutableList<Kepanitiaan> = mutableListOf()
    private lateinit var presenter: MyCommitteePresenter
    private var userId: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_previous_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
        userId = preferences.getString("user_id", null)

        sr_my_previous_committee.onRefresh {
            presenter.getMyCommittee("0", userId!!)
        }

        val scheduler = AppSchedulerProvider()
        presenter = MyCommitteePresenter(this, apiService!!, scheduler)
        presenter.getMyCommittee("0", userId!!)
    }

    override fun showMyCommittee(kepanitiaan: List<Kepanitiaan>) {
        sr_my_previous_committee.isRefreshing = false

        kepanitiaanku.clear()
        kepanitiaanku.addAll(kepanitiaan)
        val layoutManager = GridLayoutManager(ctx, 2)
        rv_my_previous_coommittee.layoutManager = layoutManager
        rv_my_previous_coommittee.adapter = MyCommitteeAdapter(kepanitiaanku, {
            val intent = Intent(context, DetailCommitteeUserActivity::class.java)
            intent.putExtra("idKegiatan", it.idKegiatan)
            startActivity(intent)
        })

    }

    override fun showloading() {
        pb_my_previous_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_my_previous_committee.visibility = View.GONE
    }

}
