package com.example.candradinatha.committee

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.DetailUserResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mService: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        mService.getDetail()
                .enqueue(object: Callback<DetailUserResponse>{
                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                        if (response.isSuccessful){
                            Toast.makeText(this@MainActivity, "${response.body()!!.email}", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

}


