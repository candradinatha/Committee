package com.example.candradinatha.committee.view.admin.approve.listapplicant

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.ListKepanitiaanAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.CutPanitiaResponse
import com.example.candradinatha.committee.model.Kepanitiaan
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_list_applicant.*
import kotlinx.android.synthetic.main.list_item_sie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListApplicantActivity : AppCompatActivity(), ListApplicantContract.View {

    private lateinit var mService: ApiInterface
    private lateinit var presenter: ListApplicantPresenter
    private var panitia: MutableList<Kepanitiaan> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_applicant)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val intent = intent
        val idSieKegiatan = intent.getStringExtra("idSieKegiatan")

        val scheduler = AppSchedulerProvider()
        presenter = ListApplicantPresenter(this, mService, scheduler)
        presenter.getUser(idSieKegiatan)

        btn_delete_sie
    }

    override fun showLoading() {
        pb_approve_list_applicant.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_approve_list_applicant.visibility = View.GONE
    }

    override fun showUser(kepanitiaan: List<Kepanitiaan>) {
        sr_list_applicant.isRefreshing = false
        panitia.clear()
        panitia.addAll(kepanitiaan)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_approve_list_applicant.layoutManager = layoutManager
        rv_approve_list_applicant.adapter = ListKepanitiaanAdapter(kepanitiaan,
                {
                    Toast.makeText(this, it.namaMahasiswa, Toast.LENGTH_LONG).show()
                },
                {
                    Toast.makeText(this, it.idKepanitiaan, Toast.LENGTH_LONG).show()
                    cutPanitia("delete", it.idKepanitiaan!!)
                })
    }

    private fun cutPanitia(role:String, idKepanitiaan:String) {
        mService.deleteKepanitiaan(role, idKepanitiaan)
                .enqueue(object : Callback<CutPanitiaResponse>{
                    override fun onFailure(call: Call<CutPanitiaResponse>, t: Throwable) {
                        Toast.makeText(this@ListApplicantActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<CutPanitiaResponse>, response: Response<CutPanitiaResponse>) {
                        Toast.makeText(this@ListApplicantActivity, "Success", Toast.LENGTH_SHORT).show()
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
