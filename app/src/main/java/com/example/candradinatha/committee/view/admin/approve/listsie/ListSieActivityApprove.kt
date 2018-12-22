package com.example.candradinatha.committee.view.admin.approve.listsie

import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.DetailSieAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Sie
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.approve.listapplicant.ListApplicantActivity

import kotlinx.android.synthetic.main.activity_list_sie_approve.*

class ListSieActivityApprove : AppCompatActivity(), ListSieContract.View {

    private lateinit var mService: ApiInterface
    private lateinit var presenter: ListSiePresenter
    private var sie: MutableList<Sie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sie_approve)
        supportActionBar?.title = "Daftar Sie"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val intent = intent
        val idKegiatan = intent.getStringExtra("idKegiatanApprove")


        val scheduler = AppSchedulerProvider()
        presenter = ListSiePresenter(this, mService, scheduler)
        presenter.getSie(idKegiatan)
    }


    override fun showLoading() {
        pb_approve_list_sie.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_approve_list_sie.visibility = View.GONE
    }

    override fun showSie(sieKegiatan: List<Sie>) {
        sr_list_sie_approve.isRefreshing = false
        sie.clear()
        sie.addAll(sieKegiatan)
        val layoutmanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_approve_list_sie.layoutManager = layoutmanager
        rv_approve_list_sie.adapter = DetailSieAdapter(sieKegiatan, {
            val intent = Intent(this, ListApplicantActivity::class.java)
            intent.putExtra("idSieKegiatan", it.idSieKegiatan)
            startActivity(intent)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}
