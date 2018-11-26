package com.example.candradinatha.committee.view.admin.committee.detail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.ActivateCommitteeResponse
import kotlinx.android.synthetic.main.activity_detail_committee.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCommitteeActivity : AppCompatActivity() {

    private lateinit var mService: ApiInterface
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
        val namaKegiatan = intent.getStringExtra("nama_kegiatan")
        val tglKegiatan = intent.getStringExtra("tgl_kegiatan")
        val tglRapat = intent.getStringExtra("tgl_rapat")
        val deskripsi = intent.getStringExtra("deskripsi")
        status = intent.getStringExtra("status")

        if (status == "1") {
            btn_active_committee.setText("non Aktivkan")
        } else {
            btn_active_committee.setText("aktivkan")
        }

        tv_detail_committee_name.setText(namaKegiatan)
        tv_detail_tanggal_kegiatan.setText(tglKegiatan)
        tv_detail_tanggal_rapat.setText(tglRapat)
        tv_detail_deskripsi.setText(deskripsi)

        btn_active_committee.setOnClickListener {
            if (status == "1") {
                role = "delete"
            } else {
                role = "active"
            }
            changeCommitteeStatus(role, id)
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

}
