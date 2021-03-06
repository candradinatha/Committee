package com.example.candradinatha.committee

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.LoginResponse
import com.example.candradinatha.committee.view.admin.AdminMainActivity
import com.example.candradinatha.committee.view.user.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var mService: ApiInterface
    private lateinit var progressBar: ProgressBar
    var fcm_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preference = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
        if (preference.contains("admin_token")){
            intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
        } else if (preference.contains("api_token")) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val pref = App.getInstance().getSharedPreferences("fcm", Context.MODE_PRIVATE)
        val fcm_token = pref.getString("fcm_token", " ")
        fcm_id = fcm_token
        Toast.makeText(this, fcm_token, Toast.LENGTH_SHORT).show()



        mService = ApiClient.client!!.create(ApiInterface::class.java)
        progressBar = progress_bar

        btn_login_submit.setOnClickListener {
            if (edt_login_username.text.toString() == ""){
                edt_login_username.setError("email field is required")
            } else if (edt_login_pass.text.toString() == "") {
                edt_login_pass.setError("password field is required")
            } else{
                showLoading()
                authenticateUser(edt_login_username.text.toString(), edt_login_pass.text.toString())}
            }

        tv_register.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun authenticateUser(email: String, password: String){
        mService.login(email, password, fcm_id)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        hideLoading()
                        Toast.makeText(this@LoginActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.code() == 502) {
                            hideLoading()
                            Toast.makeText(this@LoginActivity, "Login failed\n" + "email and password doesn't match", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            if (response.body()!!.role == "admin") {
                                hideLoading()
                                getSharedPreferences("login", Context.MODE_PRIVATE)
                                        .edit()
                                        .putString("admin_token", response.body()!!.loginToken)
                                        .putString("admin_id", response.body()!!.idMahasiswa)
                                        .apply()

                                intent = Intent(this@LoginActivity, AdminMainActivity::class.java)
                                Toast.makeText(this@LoginActivity, "${response.body()?.loginToken}", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            } else {
                                hideLoading()
                                getSharedPreferences("login", Context.MODE_PRIVATE)
                                        .edit()
                                        .putString("api_token", response.body()!!.loginToken)
                                        .putString("user_id", response.body()!!.idMahasiswa)
                                        .apply()
                                intent = Intent(this@LoginActivity, MainActivity::class.java)
                                Toast.makeText(this@LoginActivity, "${response.body()?.loginToken}", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            }
                        }
                    }

                })
    }
}
