package com.example.candradinatha.committee.view.user.profile


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
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.fragment_user_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class UserProfileFragment : Fragment() {

    private lateinit var mService: ApiInterface
    private var userId: String? = ""
    private var nim: String = ""
    private var uName:String = ""
    private var fullname: String = ""
    private var angkatan: String = ""
    private var userPass: String = ""
    private var path: String =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
        userId = preferences.getString("user_id", null)

        Toast.makeText(context, userId, Toast.LENGTH_SHORT).show()

        getUserDetail(userId!!)

        btn_user_logout.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            preferences.edit().remove("api_token").apply()
            startActivity(intent)
        }

        iv_user_profile.setOnClickListener {
            selectImage()
        }

        btn_user_edit_info.setOnClickListener {
            tv_user_username.visibility = View.GONE
            tv_user_angkatan.visibility = View.GONE
            tv_user_clear_name.visibility = View.GONE
            tv_user_nim.visibility = View.GONE

            tv_update_user_username.setText(uName)
            tv_update_user_angkatan.setText(angkatan)
            tv_update_user_clear_name.setText(fullname)
            tv_update_user_nim.setText(nim)

            tv_update_user_username.visibility = View.VISIBLE
            tv_update_user_angkatan.visibility =View.VISIBLE
            tv_update_user_clear_name.visibility = View.VISIBLE
            tv_update_user_nim.visibility = View.VISIBLE

            btn_user_edit_info.visibility = View.GONE
            btn_user_update_info.visibility = View.VISIBLE

        }

        btn_user_update_info.setOnClickListener {
            editProfile(tv_update_user_nim.text.toString(),tv_update_user_clear_name.text.toString(),tv_update_user_angkatan.text.toString(),tv_update_user_username.text.toString(),userPass )
        }
    }

    private fun getUserDetail(userId: String) {
        mService.getMemberId(userId)
                .enqueue(object : Callback<MemberResponse>{
                    override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>) {
                        tv_user_username.visibility = View.VISIBLE
                        tv_user_angkatan.visibility = View.VISIBLE
                        tv_user_clear_name.visibility = View.VISIBLE
                        tv_user_nim.visibility = View.VISIBLE
                        btn_user_edit_info.visibility = View.VISIBLE

                        tv_update_user_username.visibility = View.GONE
                        tv_update_user_angkatan.visibility =View.GONE
                        tv_update_user_clear_name.visibility = View.GONE
                        tv_update_user_nim.visibility = View.GONE
                        btn_user_update_info.visibility = View.GONE

                        Glide.with(context!!).load(response.body()!!.member.get(0).fotoMahasiswa).into(iv_user_profile)
                        tv_user_username.setText(response.body()!!.member.get(0).username)
                        tv_user_angkatan.setText(response.body()!!.member.get(0).angkatan)
                        tv_user_clear_name.setText(response.body()!!.member.get(0).namaMahasiswa)
                        tv_user_nim.setText(response.body()!!.member.get(0).nim)
                        nim = response.body()!!.member.get(0).nim!!
                        uName = response.body()!!.member.get(0).username!!
                        fullname = response.body()!!.member.get(0).namaMahasiswa!!
                        angkatan = response.body()!!.member.get(0).angkatan!!
                        userPass = response.body()!!.member.get(0).password!!
                    }

                })
    }

    private fun editProfile(nim: String, nama: String, angkatan: String, uName: String, pass: String){
        val role = RequestBody.create(MediaType.parse("text/plain"),"update")
        val idUser = RequestBody.create(MediaType.parse("text/plain"),userId)
        val nim1 = RequestBody.create(MediaType.parse("text/plain"),nim)
        val nama1 = RequestBody.create(MediaType.parse("text/plain"),nama)
        val angkatan1 = RequestBody.create(MediaType.parse("text/plain"),angkatan)
        val uName1 = RequestBody.create(MediaType.parse("text/plain"),uName)
        val pass1 = RequestBody.create(MediaType.parse("text/plain"),userPass)
        val updatedImage = MultipartBody.Part.createFormData("foto", File(path).name, RequestBody.create(MediaType.parse("foto"), File(path)))
        mService.updateProfile(role,idUser, nim1, nama1, angkatan1, uName1, pass1, updatedImage)
                .enqueue(object : Callback<MemberResponse>{
                    override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "updated successfully", Toast.LENGTH_SHORT).show()
                            getUserDetail(userId!!)
                        }else
                            Toast.makeText(context, "failed",Toast.LENGTH_SHORT).show()
                    }

                })
    }

    private fun selectImage() {
        Album.image(this)
                .singleChoice()
                .camera(true)
                .widget(
                        Widget.newDarkBuilder(context)
                                .build()
                )
                .onResult { result ->
                    result.forEach {
                        path = it.path
                        Glide.with(this).load(path).into(iv_user_profile)
                        Toast.makeText(context, path, Toast.LENGTH_SHORT).show()
                    }
                }
                .onCancel { Toast.makeText(context, "canceled", Toast.LENGTH_LONG).show() }
                .start()
    }
}
