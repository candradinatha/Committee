package com.example.candradinatha.committee.view.admin.committee.prev


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.PrevCommitteeAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.committee.detail.DetailCommitteeActivity
import kotlinx.android.synthetic.main.fragment_prev_committee.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


class PrevCommitteeFragment : Fragment(), PrevCommitteeContract.View {

    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: PrevCommitteePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_prev_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        sr_prev_committee.onRefresh {
            presenter.getCommittee("0")
        }

        val scheduler = AppSchedulerProvider()
        presenter = PrevCommitteePresenter(this, apiService!!, scheduler)
        presenter.getCommittee("0")
    }

    override fun showPrevCommittee(events: List<Committee>) {
        sr_prev_committee.isRefreshing = false
        committee.clear()
        committee.addAll(events)
        val layoutManager = GridLayoutManager(ctx, 2)
        rv_prev_coommittee.layoutManager = layoutManager
        rv_prev_coommittee.adapter = PrevCommitteeAdapter(committee,{
            getDetail(
                    "${it.idKegiatan}",
                    "${it.namaKegiatan}",
                    "${it.deskripsi}",
                    "${it.tglKegiatan}",
                    "${it.tglRapatPerdana}",
                    "${it.status}")
        })
    }

    override fun showloading() {
        pb_prev_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_prev_committee.visibility = View.GONE
    }

    private fun getDetail(id: String, namaKegiatan: String, deskripsi: String, tglKegiatan: String, tglRapat: String, status: String) {
        val intent = Intent(context, DetailCommitteeActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("nama_kegiatan", namaKegiatan)
        intent.putExtra("deskripsi", deskripsi)
        intent.putExtra("tgl_kegiatan", tglKegiatan)
        intent.putExtra("tgl_rapat", tglRapat)
        intent.putExtra("status", status)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        presenter.getCommittee("0")
    }

}
