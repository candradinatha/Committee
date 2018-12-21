package com.example.candradinatha.committee.view.user.mycommittee


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.PrevCommitteeAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteeContract
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteePresenter
import kotlinx.android.synthetic.main.fragment_my_current_committee.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class MyCurrentCommitteeFragment : Fragment(), NextCommitteeContract.View {

    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: NextCommitteePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_current_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        sr_my_next_committee.onRefresh {
            presenter.getCommittee("1")
        }

        val scheduler = AppSchedulerProvider()
        presenter = NextCommitteePresenter(this, apiService!!, scheduler)
        presenter.getCommittee("1")
    }

    override fun showPrevCommittee(kegiatan: List<Committee>) {
        sr_my_next_committee.isRefreshing = false
        committee.clear()
        committee.addAll(kegiatan)
        val layoutManager = GridLayoutManager(ctx, 2)
        rv_my_next_coommittee.layoutManager = layoutManager
        rv_my_next_coommittee.adapter = PrevCommitteeAdapter(committee,{
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
        pb_my_next_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_my_next_committee.visibility = View.GONE
    }


    private fun getDetail(id: String, namaKegiatan: String, deskripsi: String, tglKegiatan: String, tglRapat: String, status: String){

    }


}
