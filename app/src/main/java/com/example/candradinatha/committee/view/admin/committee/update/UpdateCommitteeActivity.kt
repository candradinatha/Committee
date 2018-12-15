package com.example.candradinatha.committee.view.admin.committee.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.adapter.CreateSieAdapter
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.AddCommitteeResponse
import com.example.candradinatha.committee.model.AddSieResponse
import com.example.candradinatha.committee.model.DeleteSieResponse
import com.example.candradinatha.committee.model.Sie
import com.example.candradinatha.committee.utils.AppSchedulerProvider
import com.example.candradinatha.committee.view.admin.committee.add.AddCommitteeContract
import com.example.candradinatha.committee.view.admin.committee.add.AddCommitteePresenter
import com.example.candradinatha.committee.view.admin.committee.detail.DetailCommitteeActivity
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_update_committee.*
import kotlinx.android.synthetic.main.create_new_sie.view.*
import kotlinx.android.synthetic.main.fragment_add_committee.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class UpdateCommitteeActivity : AppCompatActivity(), AddCommitteeContract.View {

    private var sie: MutableList<Sie> = mutableListOf()
    private lateinit var presenter: AddCommitteePresenter
    private lateinit var mService: ApiInterface
    var tglKegiatan: String = ""
    var tglRapat:String = ""
    var idKegiatan: String? = ""
    var path: String = ""
    var status: String? = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_committee)

        val intent = intent
        idKegiatan = intent.getStringExtra("id")

        Toast.makeText(this, idKegiatan, Toast.LENGTH_SHORT).show()

        mService = ApiClient.client!!.create(ApiInterface::class.java)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val scheduler = AppSchedulerProvider()
        presenter = AddCommitteePresenter(this, mService, scheduler)

//        update image
        btn_update_select_image.setOnClickListener {
            selectImage()
        }

//        update tanggal kegiatan
        btn_update_tanggal_kegiatan.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val nMonth = mMonth + 1
                tglKegiatan = ""+ mYear + "/" + nMonth + "/" + mDay
                tv_update_tanggal_kegiatan.setText(tglKegiatan)
            }, year, month, day)
            dpd.show()
        }

//        update rapat perdana
        btn_update_rapat_perdana.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val nMonth = mMonth + 1
                tglRapat = ""+ mYear + "/" + nMonth + "/" + mDay
                tv_update_tanggal_rapat.setText(tglRapat)
            }, year, month, day)
            dpd.show()
        }

//        add new sie
        btn_update_add_new_sie.setOnClickListener {
            showCreateSieDialog(idKegiatan!!)
        }

//        saving all updates
        btn_update_save_all.setOnClickListener {
            updateCommittee("update", idKegiatan!!, edt_nama_kegiatan.text.toString(), tglKegiatan, tglRapat, edt_deskripsi.text.toString())
            val inten = Intent(this, DetailCommitteeActivity::class.java)
            inten.putExtra("id", idKegiatan)
            inten.putExtra("status", status)
            startActivity(inten)
        }

    }

    private fun selectImage() {
        Album.image(this)
                .singleChoice()
                .camera(true)
                .widget(
                        Widget.newDarkBuilder(this)
                                .build()
                )
                .onResult { result ->
                    result.forEach {
                        path = it.path
                        Glide.with(this).load(path).into(iv_create_committee)
                        Toast.makeText(this, path, Toast.LENGTH_SHORT).show()
                    }
                }
                .onCancel { Toast.makeText(this, "canceled", Toast.LENGTH_LONG).show() }
                .start()
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
                        Toast.makeText(this@UpdateCommitteeActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddCommitteeResponse>, response: Response<AddCommitteeResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@UpdateCommitteeActivity, "created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@UpdateCommitteeActivity, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

    private fun addSie(role: String, idKegiatan: String, sie: String, job: String, kuota: String, koor: String, line: String) {
        mService.addSie(role, idKegiatan, sie, job, kuota, koor, line)
                .enqueue(object : Callback<AddSieResponse> {
                    override fun onFailure(call: Call<AddSieResponse>, t: Throwable) {
                        Toast.makeText(this@UpdateCommitteeActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddSieResponse>, response: Response<AddSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@UpdateCommitteeActivity, "${sie} created", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        } else if (response.code() == 502) {
                            Toast.makeText(this@UpdateCommitteeActivity, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }

    private fun updateSie(idSie: String, idKegiatan: String, sie: String, job: String, kuota: String, koor: String, line: String){
        mService.updateSie(idSie, idKegiatan, sie, job, kuota, koor, line)
                .enqueue(object : Callback<AddSieResponse> {
                    override fun onFailure(call: Call<AddSieResponse>, t: Throwable) {
                        Toast.makeText(this@UpdateCommitteeActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddSieResponse>, response: Response<AddSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@UpdateCommitteeActivity, "${sie} updated successfully", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        } else {
                            Toast.makeText(this@UpdateCommitteeActivity, "failed", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        }
                    }

                })
    }

    private fun deleteSie(idSie: String, idKegiatan: String){
        mService = ApiClient.client!!.create(ApiInterface::class.java)
        mService.deleteSie("delete",idSie)
                .enqueue( object : Callback<DeleteSieResponse>{
                    override fun onFailure(call: Call<DeleteSieResponse>, t: Throwable) {
                        Toast.makeText(this@UpdateCommitteeActivity, "Ops.. Something went wrong with your internet connection", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DeleteSieResponse>, response: Response<DeleteSieResponse>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@UpdateCommitteeActivity, "deleted succesfully", Toast.LENGTH_SHORT).show()
                            presenter.getSie(idKegiatan)
                        }
                        else
                            Toast.makeText(this@UpdateCommitteeActivity, "failed", Toast.LENGTH_SHORT).show()
                    }


                })
    }

    private fun showCreateSieDialog(idKegiatan: String){
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val subView: View = inflater.inflate(R.layout.create_new_sie, null)

        val builder = AlertDialog.Builder(this)
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
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val subView: View = inflater.inflate(R.layout.create_new_sie, null)
        subView.edt_create_sie_name.setText(sie)
        subView.edt_create_sie_job.setText(job)
        subView.edt_create_sie_kuota.setText(kuota)
        subView.edt_create_sie_koor.setText(koor)
        subView.edt_create_sie_line.setText(line)

        val builder = AlertDialog.Builder(this)
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
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure wanna delete ${sie}?")
                .setPositiveButton("Yes"){ dialog, id ->
                    deleteSie(idSie,idKegiatan)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    override fun showSie(sieKegiatan: List<Sie>) {
        sie.clear()
        sie.addAll(sieKegiatan)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_list_sie.layoutManager = layoutManager
        rv_list_sie.adapter = CreateSieAdapter(sieKegiatan,{
            showUpdateSieDialog(it.idSieKegiatan!!, it.idKegiatan!!, it.sie!!, it.jobDesc!!, it.kuota!!, it.namaKoor!!, it.idLineKoor!!)
        },{
            showDeleteSieDialog(it.idSieKegiatan!!, it.sie!!, it.idKegiatan!!)
        })
    }
}
