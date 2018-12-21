package com.example.candradinatha.committee.view.user.committee

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.App
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.DetailSieUserAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.model.JoinResponse
import com.example.candradinatha.committee.model.Sie
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.utils.showIndonesianDateTime
import com.example.candradinatha.committee.utils.toGMTFormat
import com.example.candradinatha.committee.view.admin.committee.detail.DetailCommitteeContract
import com.example.candradinatha.committee.view.admin.committee.detail.DetailCommitteePresenter
import kotlinx.android.synthetic.main.activity_detail_committee_user.*
import kotlinx.android.synthetic.main.input_join.view.*
import kotlinx.android.synthetic.main.list_item_sie_detail_user.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.onRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailCommitteeUserActivity : AppCompatActivity(), DetailCommitteeContract.View {

    private lateinit var mService: ApiInterface
    private var sie: MutableList<Sie> = mutableListOf()
    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: DetailCommitteePresenter
    private var status: String = ""
    private var role: String =""
    private var userId: String? = ""
    private var tanggalSekarang: String =""
    private var idKegiatan: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_committee_user)
        supportActionBar?.title = "Detail Kegiatan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
        userId = preferences.getString("user_id", null)

        val intent = intent
        val id = intent.getStringExtra("idKegiatan")

        idKegiatan = id

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val mMonth = month + 1

        tanggalSekarang = ""+ year + "/" + mMonth + "/" + day

        sr_user_detail_committee.onRefresh {
            presenter.getSie(id)
            presenter.getCommittee(id)
        }

        val scheduler = AppSchedulerProvider()
        presenter = DetailCommitteePresenter(this, mService, scheduler)
        presenter.getSie(id)
        presenter.getCommittee(id)

    }

    override fun showLoading() {
        sr_user_detail_committee.isRefreshing = true
    }

    override fun hideLoading() {
        sr_user_detail_committee.isRefreshing = false
    }

    override fun showSie(sieKegiatan: List<Sie>) {
        sr_user_detail_committee.isRefreshing = false
        sie.clear()
        sie.addAll(sieKegiatan)
        val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_user_list_sie_detail.layoutManager = layoutManager
        rv_user_list_sie_detail.adapter = DetailSieUserAdapter(sieKegiatan, {
            showJoin(userId!!, it.idSieKegiatan!!)
        })

    }

    override fun showCommittee(kegiatan: List<Committee>) {
        sr_user_detail_committee.isRefreshing = false
        committee.clear()
        committee.addAll(kegiatan)

        val tangKegiatan = toGMTFormat(committee.get(0).tglKegiatan)
        val tangRapat = toGMTFormat(committee.get(0).tglRapatPerdana)
        val patternDate = "EEEE, dd MMMM yyyy"

        val tanggalKegiatan: String = showIndonesianDateTime(tangKegiatan, patternDate)
        val tanggalRapat: String = showIndonesianDateTime(tangRapat, patternDate)



        tv_user_detail_committee_name.text = committee.get(0).namaKegiatan
        tv_user_detail_tanggal_rapat.text = tanggalRapat
        tv_user_detail_deskripsi.text = committee.get(0).deskripsi
        tv_user_detail_tanggal_kegiatan.text = tanggalKegiatan
        Glide.with(this).load(committee.get(0).fotoKegiatan).into(iv_user_committee_detail)
    }

    private fun showJoin(idMahasiswa: String, idSie: String) {
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val subView: View = inflater.inflate(R.layout.input_join, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(subView)
        builder.setPositiveButton("Daftar"){ dialog, id ->
                    val alasan = subView.edt_alasan_daftar.text.toString()

                    joinKegiatan(idMahasiswa,idSie, tanggalSekarang, alasan)
                }
                .setNegativeButton("Batal") { dialog, id ->
                    presenter.getSie(idKegiatan)
                }
        builder.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun joinKegiatan(idMahasiswa: String, idSie: String, tanggalSekarang:String, alasan: String) {
        mService.joinKepanitiaan(idMahasiswa, idSie, tanggalSekarang, alasan)
                .enqueue(object : Callback<JoinResponse>{
                    override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                        Toast.makeText(this@DetailCommitteeUserActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                        if (response.isSuccessful){
                            Toast.makeText(this@DetailCommitteeUserActivity, "Success", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

}
