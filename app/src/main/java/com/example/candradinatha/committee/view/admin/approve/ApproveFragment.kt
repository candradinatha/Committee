package com.example.candradinatha.committee.view.admin.approve


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.ApproveCommitteeAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.approve.listsie.ListSieActivityApprove
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteeContract
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteePresenter
import kotlinx.android.synthetic.main.fragment_approve.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


class ApproveFragment : Fragment(), NextCommitteeContract.View {

    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: NextCommitteePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approve, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        sr_approve_committee.onRefresh {
            presenter.getCommittee("1")
        }

        val scheduler = AppSchedulerProvider()
        presenter = NextCommitteePresenter(this, apiService!!, scheduler)
        presenter.getCommittee("1")
    }

    override fun showPrevCommittee(kegiatan: List<Committee>) {
        sr_approve_committee.isRefreshing = false
        committee.clear()
        committee.addAll(kegiatan)
        val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_approve_coommittee.layoutManager = layoutManager
        rv_approve_coommittee.adapter = ApproveCommitteeAdapter(committee, {
            Toast.makeText(context, it.namaKegiatan, Toast.LENGTH_SHORT).show()
            val intent = Intent(view!!.context, ListSieActivityApprove::class.java)
            intent.putExtra("idKegiatanApprove", it.idKegiatan)
            activity!!.startActivity(intent)
        })
    }

    override fun showloading() {
        pb_approve_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_approve_committee.visibility = View.GONE
    }
}
