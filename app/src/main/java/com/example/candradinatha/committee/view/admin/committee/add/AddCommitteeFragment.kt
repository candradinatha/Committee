package com.example.candradinatha.committee.view.admin.committee.add


import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.AddCommitteeResponse
import kotlinx.android.synthetic.main.fragment_add_committee.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.model.AddSieResponse
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddCommitteeFragment : Fragment() {

    private lateinit var mService: ApiInterface
    var tglKegiatan: String = ""
    var tglRapat:String = ""
    var idKegiata: String? = ""
    var path: String = ""
    private var mAlbumFiles: ArrayList<AlbumFile>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_committee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val rol : String = "insert"


        btn_select_image.setOnClickListener {
            selectImage()
        }

//tangal kegiatan
        btn_tanggal_kegiatan.setOnClickListener {
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                tglKegiatan = ""+ mYear + "/" + mMonth + "/" + mDay
                tv_tanggal_kegiatan.setText(tglKegiatan)
            }, year, month, day)
            dpd.show()
        }
//tanggal rapat
        btn_rapat_perdana.setOnClickListener {
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                tglRapat = ""+ mYear + "/" + mMonth + "/" + mDay
                tv_tanggal_rapat.setText(tglRapat)
            }, year, month, day)
            dpd.show()
        }
//tambah sie simpan kegiatan
        btn_submit.setOnClickListener {
            addNewCommittee(rol, edt_nama_kegiatan.text.toString(), tglKegiatan, tglRapat, edt_deskripsi.text.toString())
            list_sie.visibility = View.VISIBLE
        }

//        tambah sekre
        cb_sekre.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true) {
                list_input_sekre.visibility = View.VISIBLE
            }
            else list_input_sekre.visibility = View.GONE
        }

        cb_pubdok.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true) {
                list_input_pubdok.visibility = View.VISIBLE
            }
            else list_input_pubdok.visibility = View.GONE
        }

        btn_submit_sekre.setOnClickListener {
            addSie("1","Sie Sekretariat", edt_job_sekre.text.toString(), edt_kuota_sekre.text.toString(),edt_koor_sekre.text.toString(),edt_line_sekre.text.toString())
        }

    }


    private fun addNewCommittee(role: String, namaKegiatan: String, tglKegiatan: String, tglRapat: String, deskripsi: String) {
        val role1 = RequestBody.create(MediaType.parse("text/plain"),role)
        val namaKegiatan1 = RequestBody.create(MediaType.parse("text/plain"),namaKegiatan)
        val tglKegiatan1 = RequestBody.create(MediaType.parse("text/plain"),tglKegiatan)
        val tglRapat1 = RequestBody.create(MediaType.parse("text/plain"),tglRapat)
        val deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),deskripsi)
        val image = MultipartBody.Part.createFormData("foto", File(path).name, RequestBody.create(MediaType.parse("foto"), File(path)))



        mService.addCommittee(role1, namaKegiatan1, tglKegiatan1, tglRapat1, deskripsi1, image)
                .enqueue(object : Callback<AddCommitteeResponse>{
                    override fun onFailure(call: Call<AddCommitteeResponse>, t: Throwable) {
                        Toast.makeText(activity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddCommitteeResponse>, response: Response<AddCommitteeResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(activity, "created successfully", Toast.LENGTH_SHORT).show()
                        } else if (response.code() == 502) {
                            Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

    private fun addSie(id: String, sie: String, job: String, kuota: String, koor: String, line: String) {
        mService.addSie(id, sie, job, kuota, koor, line)
                .enqueue(object : Callback<AddSieResponse>{
                    override fun onFailure(call: Call<AddSieResponse>, t: Throwable) {
                        Toast.makeText(activity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddSieResponse>, response: Response<AddSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(activity, "${sie} created", Toast.LENGTH_SHORT).show()
                        } else if (response.code() == 502) {
                            Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()
                        }
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
                        Glide.with(context!!).load(path).into(iv_create_committee)
                        Toast.makeText(context, path, Toast.LENGTH_SHORT).show()
                    }
                }
                .onCancel { Toast.makeText(context, "canceled", Toast.LENGTH_LONG).show() }
                .start()
    }


}
