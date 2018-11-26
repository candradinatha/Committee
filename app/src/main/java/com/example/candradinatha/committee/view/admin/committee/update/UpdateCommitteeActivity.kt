package com.example.candradinatha.committee.view.admin.committee.update

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface

class UpdateCommitteeActivity : AppCompatActivity() {

    private lateinit var mService: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_committee)

        mService = ApiClient.client!!.create(ApiInterface::class.java)
    }
}
