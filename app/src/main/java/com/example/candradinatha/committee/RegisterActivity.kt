package com.example.candradinatha.committee

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mService = ApiClient.client!!.create(ApiInterface::class.java)
        progressBar = progress_bar

        val pref = App.getInstance().getSharedPreferences("fcm", Context.MODE_PRIVATE)
        val fcm_token = pref.getString("fcm_token", " ")
        Toast.makeText(this, fcm_token, Toast.LENGTH_SHORT).show()

        edt_name.setText(fcm_token)

        btn_register_submit.setOnClickListener {
            if (edt_name.text.toString() == "")
                tl_nama.setError("name field is required")
            else if (edt_nim.text.toString() == "")
                tl_nim.setError("nim field is required")
            else if (edt_pass.text.toString() == "")
                tl_password.setError("password field is required")
            else if (edt_repass.text.toString() == "")
                tl_repeat_pass.setError("password field is required")
            else if (edt_angkatan.text.toString() == "")
                tl_angkatan.setError("password field is required")
            else if (edt_username.text.toString() == "")
                tl_uname.setError("password field is required")
            else if (edt_pass.text.toString() != edt_repass.text.toString()) {
                tl_password.setError("pass doesn't match")
            } else {
                showLoading()
                createNewUser("insert", edt_nim.text.toString(), edt_name.text.toString(), edt_angkatan.text.toString(), edt_username.text.toString(), edt_pass.text.toString())
            }
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun createNewUser(role:String, nim: String, nama: String, angkatan: String, username: String, password: String) {
        mService.register(role, nim, nama, angkatan, username, password)
                .enqueue(object: Callback<RegisterResponse>{
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        hideLoading()
                        Toast.makeText(this@RegisterActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.body()!!.message == "Registered") {
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
