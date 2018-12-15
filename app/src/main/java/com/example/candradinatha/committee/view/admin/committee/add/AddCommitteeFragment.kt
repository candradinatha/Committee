package com.example.candradinatha.committee.view.admin.committee.add


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.example.candradinatha.committee.adapter.CreateSieAdapter
import com.example.candradinatha.committee.model.AddSieResponse
import com.example.candradinatha.committee.model.DeleteSieResponse
import com.example.candradinatha.committee.model.Sie
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.committee.detail.DetailCommitteeActivity
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.create_new_sie.*
import kotlinx.android.synthetic.main.create_new_sie.view.*
import kotlinx.android.synthetic.main.list_item_sie.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.ctx
import java.io.File


class AddCommitteeFragment : Fragment(), AddCommitteeContract.View {

    private var sie: MutableList<Sie> = mutableListOf()
    private lateinit var presenter: AddCommitteePresenter
    private lateinit var mService: ApiInterface
    var tglKegiatan: String = ""
    var tglRapat:String = ""
    var idKegiatan: String? = ""
    var path: String = ""
    var status: String? = "1"

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

        val scheduler = AppSchedulerProvider()
        presenter = AddCommitteePresenter(this, mService, scheduler)

        btn_select_image.setOnClickListener {
            selectImage()
        }

//        tangal kegiatan
        btn_tanggal_kegiatan.setOnClickListener {
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val nMonth = mMonth + 1
                tglKegiatan = ""+ mYear + "/" + nMonth + "/" + mDay
                tv_tanggal_kegiatan.setText(tglKegiatan)
            }, year, month, day)
            dpd.show()
        }
//        tanggal rapat
        btn_rapat_perdana.setOnClickListener {
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val nMonth = mMonth + 1
                tglRapat = ""+ mYear + "/" + nMonth + "/" + mDay
                tv_tanggal_rapat.setText(tglRapat)
            }, year, month, day)
            dpd.show()
        }
//        tambah sie simpan kegiatan
        btn_save_committee.setOnClickListener {
            btn_save_committee.visibility = View.GONE
            btn_add_new_sie.visibility = View.VISIBLE
            addNewCommittee("insert", edt_nama_kegiatan.text.toString(), tglKegiatan, tglRapat, edt_deskripsi.text.toString())
            btn_save_all.visibility = View.VISIBLE
        }

        btn_add_new_sie.setOnClickListener {
            showCreateSieDialog(idKegiatan!!)
        }

        btn_save_all.setOnClickListener {
            updateCommittee("update", idKegiatan!!, edt_nama_kegiatan.text.toString(), tglKegiatan, tglRapat, edt_deskripsi.text.toString())
            val intent = Intent(context, DetailCommitteeActivity::class.java)
            intent.putExtra("id", idKegiatan)
            intent.putExtra("status", status)
            startActivity(intent)
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
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddCommitteeResponse>, response: Response<AddCommitteeResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "created successfully", Toast.LENGTH_SHORT).show()
                            idKegiatan = response.body()!!.idKegiatan
                            showCreateSieDialog(idKegiatan!!)
                        } else if (response.code() == 502) {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

    private fun updateCommittee(role: String, id: String, namaKegiatan: String, tglKegiatan: String, tglRapat: String, deskripsi: String){
        val role1 = RequestBody.create(MediaType.parse("text/plain"),role)
        val idKegiatan1 = RequestBody.create(MediaType.parse("text/plain"), id)
        val namaKegiatan1 = RequestBody.create(MediaType.parse("text/plain"),namaKegiatan)
        val tglKegiatan1 = RequestBody.create(MediaType.parse("text/plain"),tglKegiatan)
        val tglRapat1 = RequestBody.create(MediaType.parse("text/plain"),tglRapat)
        val deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),deskripsi)
        val image = MultipartBody.Part.createFormData("foto", File(path).name, RequestBody.create(MediaType.parse("foto"), File(path)))

        mService.updateCommittee(role1, idKegiatan1, namaKegiatan1, tglKegiatan1, tglRapat1,deskripsi1, image)
                .enqueue(object : Callback<AddCommitteeResponse>{
                    override fun onFailure(call: Call<AddCommitteeResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddCommitteeResponse>, response: Response<AddCommitteeResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

    private fun addSie(role: String, idKegiatan: String, sie: String, job: String, kuota: String, koor: String, line: String) {
        mService.addSie(role, idKegiatan, sie, job, kuota, koor, line)
                .enqueue(object : Callback<AddSieResponse>{
                    override fun onFailure(call: Call<AddSieResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddSieResponse>, response: Response<AddSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "${sie} created", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        } else if (response.code() == 502) {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

    private fun updateSie(idSie: String, idKegiatan: String, sie: String, job: String, kuota: String, koor: String, line: String){
        mService.updateSie(idSie, idKegiatan, sie, job, kuota, koor, line)
                .enqueue(object : Callback<AddSieResponse> {
                    override fun onFailure(call: Call<AddSieResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddSieResponse>, response: Response<AddSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "${sie} updated successfully", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        } else {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
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

    override fun showSie(sieKegiatan: List<Sie>) {
        sie.clear()
        sie.addAll(sieKegiatan)
        val layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
        rv_list_sie.layoutManager = layoutManager
        rv_list_sie.adapter = CreateSieAdapter(sieKegiatan,{
            showUpdateSieDialog(it.idSieKegiatan!!, it.idKegiatan!!, it.sie!!, it.jobDesc!!, it.kuota!!, it.namaKoor!!, it.idLineKoor!!)
        },{
            showDeleteSieDialog(it.idSieKegiatan!!, it.sie!!, it.idKegiatan!!)
        })
    }

    private fun showCreateSieDialog(idKegiatan: String){
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val subView: View = inflater.inflate(R.layout.create_new_sie, null)

        val builder = AlertDialog.Builder(context)
        builder.setView(subView)
        builder.setPositiveButton("Yes"){ dialog, id ->
                    val namaSie = subView.edt_create_sie_name.text.toString()
                    val namaKoor = subView.edt_create_sie_koor.text.toString()
                    val lineKoor = subView.edt_create_sie_line.text.toString()
                    val kuota = subView.edt_create_sie_kuota.text.toString()
                    val jobDesc = subView.edt_create_sie_job.text.toString()

                    addSie("insert",idKegiatan,namaSie,jobDesc,kuota,namaKoor, lineKoor)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    presenter.getSie(idKegiatan)
                }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun showUpdateSieDialog(idSie: String, idKegiatan: String, sie: String, job: String, kuota: String, koor: String, line: String){
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val subView: View = inflater.inflate(R.layout.create_new_sie, null)
        subView.edt_create_sie_name.setText(sie)
        subView.edt_create_sie_job.setText(job)
        subView.edt_create_sie_kuota.setText(kuota)
        subView.edt_create_sie_koor.setText(koor)
        subView.edt_create_sie_line.setText(line)

        val builder = AlertDialog.Builder(context)
        builder.setView(subView)
        builder.setPositiveButton("Yes"){ dialog, id ->
            val namaSie = subView.edt_create_sie_name.text.toString()
            val namaKoor = subView.edt_create_sie_koor.text.toString()
            val lineKoor = subView.edt_create_sie_line.text.toString()
            val kuotaSie = subView.edt_create_sie_kuota.text.toString()
            val jobDesc = subView.edt_create_sie_job.text.toString()

            updateSie(idSie, idKegiatan, namaSie, jobDesc, kuotaSie, namaKoor, lineKoor)
        }
                .setNegativeButton("Cancel") { dialog, id ->
                    presenter.getSie(idKegiatan)
                }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun showDeleteSieDialog(idSie: String, sie: String, idKegiatan: String){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure wanna delete ${sie}?")
                .setPositiveButton("Yes"){ dialog, id ->
                    deleteSie(idSie,idKegiatan)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun deleteSie(idSie: String, idKegiatan: String){
        mService = ApiClient.client!!.create(ApiInterface::class.java)
        mService.deleteSie("delete",idSie)
                .enqueue( object : Callback<DeleteSieResponse>{
                    override fun onFailure(call: Call<DeleteSieResponse>, t: Throwable) {
                        Toast.makeText(context, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DeleteSieResponse>, response: Response<DeleteSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "deleted succesfully", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        }
                        else
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                    }


                })
    }

}
