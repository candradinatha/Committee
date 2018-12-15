package com.example.candradinatha.committee.view.admin.committee.next


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
import kotlinx.android.synthetic.main.fragment_next_committee.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class NextCommitteeFragment : Fragment(), NextCommitteeContract.View {

    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: NextCommitteePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        sr_next_committee.onRefresh {
            presenter.getCommittee("1")
        }

        val scheduler = AppSchedulerProvider()
        presenter = NextCommitteePresenter(this, apiService!!, scheduler)
        presenter.getCommittee("1")
    }

    override fun showPrevCommittee(kegiatan: List<Committee>) {
        sr_next_committee.isRefreshing = false
        committee.clear()
        committee.addAll(kegiatan)
        val layoutManager = GridLayoutManager(ctx, 2)
        rv_next_coommittee.layoutManager = layoutManager
        rv_next_coommittee.adapter = PrevCommitteeAdapter(committee,{
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
        pb_next_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_next_committee.visibility = View.GONE
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
        presenter.getCommittee("1")
    }

}
