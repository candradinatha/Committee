package com.example.candradinatha.committee.view.admin.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.App
import com.example.candradinatha.committee.LoginActivity

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.MemberResponse
import kotlinx.android.synthetic.main.fragment_user_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminProfileFragment : Fragment() {

    private lateinit var mService: ApiInterface
    private var userId: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
        userId = preferences.getString("admin_id", null)

        Toast.makeText(context, userId, Toast.LENGTH_SHORT).show()

        getUserDetail(userId!!)

        btn_user_logout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            preferences.edit().remove("admin_token").apply()
            startActivity(intent)
        }
    }

    private fun getUserDetail(userId: String) {
        mService.getMemberId(userId)
                .enqueue(object : Callback<MemberResponse> {
                    override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>) {
                        Glide.with(context!!).load(response.body()!!.member.get(0).fotoMahasiswa).into(iv_user_profile)
                        tv_user_username.setText(response.body()!!.member.get(0).username)
                        tv_user_angkatan.setText(response.body()!!.member.get(0).angkatan)
                        tv_user_clear_name.setText(response.body()!!.member.get(0).namaMahasiswa)
                        tv_user_nim.setText(response.body()!!.member.get(0).nim)
                    }

                })
    }
}
