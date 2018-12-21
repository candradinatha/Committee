package com.example.candradinatha.committee.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Kepanitiaan
import kotlinx.android.synthetic.main.list_mycommittee.view.*

class MyCommitteeAdapter(private val kepanitiaan: List<Kepanitiaan>, private val listener: (Kepanitiaan) -> Unit)

    : RecyclerView.Adapter<MyCommitteeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommitteeViewHolder {
        return MyCommitteeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_mycommittee, parent, false))
    }

    override fun getItemCount(): Int {
        return kepanitiaan.size
    }

    override fun onBindViewHolder(holder: MyCommitteeViewHolder, position: Int) {
        holder.bindItem(kepanitiaan[position], listener)
    }
}

class MyCommitteeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(kepanitiaan: Kepanitiaan, listener: (Kepanitiaan) -> Unit) {
        itemView.tv_kegiatan_detail.setText(kepanitiaan.namaKegiatan)
        itemView.tv_sie_koor_detail.setText(kepanitiaan.namaKoor)
        itemView.tv_sie_kuota_detail.setText(kepanitiaan.kuota)
        itemView.tv_sie_line_detail.setText(kepanitiaan.idLineKoor)
        itemView.tv_sie_name_detail.setText(kepanitiaan.sie)
        itemView.tv_tanggal_kegiatan.setText(kepanitiaan.tglKegiatan)
        itemView.tv_rapat_perdana.setText(kepanitiaan.tglRapatPerdana)
    }
}