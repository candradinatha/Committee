package com.example.candradinatha.committee.view.admin.approve.listsie


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.DetailSieAdapter
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Sie
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_approve.*
import kotlinx.android.synthetic.main.fragment_list_sie.*
import org.jetbrains.anko.ctx

class ListSieFragment : Fragment(), ListSieContract.View {

    private lateinit var mService: ApiInterface
    private lateinit var presenter: ListSiePresenter
    private var sie: MutableList<Sie> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_sie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val intent = activity!!.intent
        val idKegiatan = intent.getStringExtra("idKegiatanApprove")

        val scheduler = AppSchedulerProvider()
        presenter = ListSiePresenter(this, mService, scheduler)
        presenter.getSie(idKegiatan)
    }


    override fun showLoading() {
        pb_approve_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_approve_committee.visibility = View.GONE
    }

    override fun showSie(sieKegiatan: List<Sie>) {
        sr_approve_committee.isRefreshing = false
        sie.clear()
        sie.addAll(sieKegiatan)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_approve_list_sie.layoutManager = layoutManager
        rv_approve_list_sie.adapter = DetailSieAdapter(sieKegiatan, {
            Toast.makeText(context, it.sie, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            activity!!.finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}
