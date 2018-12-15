package com.example.candradinatha.committee.view.admin.committee.detail

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.DetailSieAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.ActivateCommitteeResponse
import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.model.Sie
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.committee.add.AddCommitteePresenter
import com.example.candradinatha.committee.view.admin.committee.update.UpdateCommitteeActivity
import kotlinx.android.synthetic.main.activity_detail_committee.*
import kotlinx.android.synthetic.main.create_new_sie.view.*
import kotlinx.android.synthetic.main.sie_detail.view.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.onRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCommitteeActivity : AppCompatActivity(), DetailCommitteeContract.View {

    private lateinit var mService: ApiInterface
    private var sie: MutableList<Sie> = mutableListOf()
    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: DetailCommitteePresenter
    private var status: String = ""
    private var role: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_committee)
        supportActionBar?.title = "Committee Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val intent = intent
        val id = intent.getStringExtra("id")
        status = intent.getStringExtra("status")

        val scheduler = AppSchedulerProvider()
        presenter = DetailCommitteePresenter(this, mService, scheduler)
        presenter.getSie(id)
        presenter.getCommittee(id)

        if (status == "1") {
            btn_active_committee.setText("non Aktivkan")
        } else {
            btn_active_committee.setText("aktivkan")
        }

        btn_active_committee.setOnClickListener {
            if (status == "1") {
                role = "delete"
            } else {
                role = "active"
            }
            changeCommitteeStatus(role, id)
        }

        btn_update_committe.setOnClickListener {
            val intn = Intent(this, UpdateCommitteeActivity::class.java)
            intn.putExtra("id", id)
            startActivity(intn)
        }

        sr_detail.onRefresh {
            presenter.getSie(id)
            presenter.getCommittee(id)
        }

    }

    private fun changeCommitteeStatus(mRole: String, id: String) {
        mService.updateStatus(mRole, id)
                .enqueue(object : Callback<ActivateCommitteeResponse>{
                    override fun onFailure(call: Call<ActivateCommitteeResponse>, t: Throwable) {
                        Toast.makeText(this@DetailCommitteeActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ActivateCommitteeResponse>, response: Response<ActivateCommitteeResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@DetailCommitteeActivity, "updated successfully", Toast.LENGTH_SHORT).show()
                            status = response.body()!!.status!!
                            if (status == "1") {
                                btn_active_committee.setText("non Aktivkan")
                            } else {
                                btn_active_committee.setText("aktivkan")
                            }

                        } else
                            Toast.makeText(this@DetailCommitteeActivity, "failed", Toast.LENGTH_SHORT).show()
                    }

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

    override fun showSie(sieKegiatan: List<Sie>) {
        sr_detail.isRefreshing = false
        sie.clear()
        sie.addAll(sieKegiatan)
        val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_list_sie_detail.layoutManager = layoutManager
        rv_list_sie_detail.adapter = DetailSieAdapter(sieKegiatan, {
            showSieDetailDialog(it.sie!!, it.jobDesc!!, it.kuota!!, it.namaKoor!!, it.idLineKoor!!)
        })
    }

    override fun showCommittee(kegiatan: List<Committee>) {
        sr_detail.isRefreshing = false
        committee.clear()
        committee.addAll(kegiatan)

        tv_detail_committee_name.text = committee.get(0).namaKegiatan
        tv_detail_tanggal_rapat.text = committee.get(0).tglRapatPerdana
        tv_detail_deskripsi.text = committee.get(0).deskripsi
        tv_detail_tanggal_kegiatan.text = committee.get(0).tglKegiatan
        Glide.with(this).load(committee.get(0).fotoKegiatan).into(iv_committee_detail)
    }

    override fun showLoading() {
        sr_detail.isRefreshing = true
    }

    override fun hideLoading() {
        sr_detail.isRefreshing = false
    }

    private fun showSieDetailDialog(sie: String, job: String, kuota: String, koor: String, line: String){
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val subView: View = inflater.inflate(R.layout.sie_detail, null)
        subView.tv_detail_sie_name.setText(sie)
        subView.tv_detail_sie_job.setText(job)
        subView.tv_detail_sie_kuota.setText(kuota)
        subView.tv_detail_sie_koor.setText(koor)
        subView.tv_detail_sie_line.setText(line)

        val builder = AlertDialog.Builder(this)
        builder.setView(subView)
        // Create the AlertDialog object and return it
        builder.create().show()
    }

}
