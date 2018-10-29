package com.example.candradinatha.committee

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var mService: ApiInterface
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mService = ApiClient.client!!.create(ApiInterface::class.java)
        progressBar = progress_bar

        btn_login_submit.setOnClickListener {
            if (edt_email.text.toString() == ""){
                edt_email.setError("email field is required")
            } else if (edt_pass.text.toString() == "") {
                edt_pass.setError("password field is required")
            } else{
                showLoading()
                authenticateUser(edt_email.text.toString(), edt_pass.text.toString())}
            }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun authenticateUser(email: String, password: String){
        mService.login(email, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        hideLoading()
                        Toast.makeText(this@LoginActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (!response.body()!!.status) {
                            hideLoading()
                            Toast.makeText(this@LoginActivity, "Login failed\n" + "email and password doesn't match", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            if (response.body()!!.admin == 1) {
                                hideLoading()
                                intent = Intent(this@LoginActivity, AdminMainActivity::class.java)
                                Toast.makeText(this@LoginActivity, "Login Admin", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            } else {
                                hideLoading()
                                intent = Intent(this@LoginActivity, MainActivity::class.java)
                                Toast.makeText(this@LoginActivity, "Login User", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            }
                        }
                    }

                })
    }
}