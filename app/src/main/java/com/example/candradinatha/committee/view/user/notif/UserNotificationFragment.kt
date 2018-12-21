package com.example.candradinatha.committee.view.user.notif


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.candradinatha.committee.App

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.NotifAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.Notification
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_user_notification.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class UserNotificationFragment : Fragment(), UserNotificationContract.View {

    private var notif: MutableList<Notification> = mutableListOf()
    private lateinit var presenter: UserNotificationPresenter
    private var userId: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = ApiClient.client?.create(ApiInterface::class.java)

        val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
        userId = preferences.getString("user_id", null)

        val scheduler = AppSchedulerProvider()
        presenter = UserNotificationPresenter(this, apiService!!, scheduler)
        presenter.getNotif(userId!!)

        sr_notif.onRefresh {
            presenter.getNotif(userId!!)
        }
    }

    override fun showNotif(pemberitahuan: List<Notification>) {
        sr_notif.isRefreshing = false
        notif.clear()
        notif.addAll(pemberitahuan)
        val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_notif.layoutManager = layoutManager
        rv_notif.adapter = NotifAdapter(notif, {})
    }

    override fun showloading() {
        pb_notif.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_notif.visibility = View.GONE
    }
}
