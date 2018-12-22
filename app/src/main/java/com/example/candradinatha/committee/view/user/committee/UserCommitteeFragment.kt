package com.example.candradinatha.committee.view.user.committee


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.PrevCommitteeAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.utils.isNetworkAvailable
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteeContract
import com.example.candradinatha.committee.view.admin.committee.next.NextCommitteePresenter
import kotlinx.android.synthetic.main.fragment_user_committee.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh


class UserCommitteeFragment : Fragment(), NextCommitteeContract.View {

    private var committee: MutableList<Committee> = mutableListOf()
    private lateinit var presenter: NextCommitteePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        val scheduler = AppSchedulerProvider()
        presenter = NextCommitteePresenter(this, apiService!!, scheduler)

        sr_user_committee.onRefresh {
//            if (isNetworkAvailable(context)){
                presenter.getCommittee("1")
//            }
//            else{
//                getLocalData()
//            }
        }

//        if (isNetworkAvailable(context)){
            presenter.getCommittee("1")
//        } else{
//            getLocalData()
//        }
    }

    override fun showPrevCommittee(kegiatan: List<Committee>) {
        sr_user_committee.isRefreshing = false
        committee.clear()
        committee.addAll(kegiatan)
        val layoutManager = GridLayoutManager(ctx, 2)
        rv_user_coommittee.layoutManager = layoutManager
        rv_user_coommittee.adapter = PrevCommitteeAdapter(committee, {
            val intent = Intent(context, DetailCommitteeUserActivity::class.java)
            intent.putExtra("idKegiatan", it.idKegiatan)
            startActivity(intent)
        })
    }

//    private fun getLocalData(){
//
//    }

    override fun showloading() {
        pb_user_committee.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_user_committee.visibility = View.GONE
    }

}
