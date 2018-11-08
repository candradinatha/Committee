package com.example.candradinatha.committee

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.LoginResponse
import com.example.candradinatha.committee.model.RegisterResponse
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var mService: ApiInterface
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mService = ApiClient.client!!.create(ApiInterface::class.java)
        progressBar = progress_bar

        btn_register_submit.setOnClickListener {
            if (edt_name.text.toString() == "")
                edt_name.setError("name field is required")
            else if (edt_email.text.toString() == "")
                edt_email.setError("email field is required")
            else if (edt_pass.text.toString() == "")
                edt_pass.setError("password field is required")
            else if (edt_repass.text.toString() == "")
                edt_repass.setError("password field is required")
            if (edt_pass.text.toString() != edt_repass.text.toString()) {
                edt_repass.setError("pass doesn't match")
            } else {
                showLoading()
                createNewUser(edt_name.text.toString(), edt_email.text.toString(), edt_pass.text.toString())
            }
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun createNewUser(name: String, email: String, password: String) {
        mService.register(name, email, password)
                .enqueue(object: Callback<RegisterResponse>{
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        hideLoading()
                        Toast.makeText(this@RegisterActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.body()!!.status) {
                            hideLoading()
                            intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            Toast.makeText(this@RegisterActivity, "Register Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        } else
                            hideLoading()
                            Toast.makeText(this@RegisterActivity, "Register Gagal", Toast.LENGTH_SHORT).show()
                    }

                })
    }
}
