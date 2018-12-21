package com.example.candradinatha.committee.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Sie
import kotlinx.android.synthetic.main.list_item_sie_detail_user.view.*

class DetailSieUserAdapter(private val sieKegiatan: List<Sie>, private val listener: (Sie) -> Unit)
    : RecyclerView.Adapter<DetailSieUserViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSieUserViewHolder {
        return DetailSieUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_sie_detail_user, parent, false))
    }

    override fun getItemCount(): Int {
        return sieKegiatan.size
    }

    override fun onBindViewHolder(holder: DetailSieUserViewHolder, position: Int) {
        holder.bindItem(sieKegiatan[position], listener)
    }
}

class DetailSieUserViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(sie: Sie, listener: (Sie) -> Unit) {
        itemView.tv_sie_koor_detail.setText(sie.namaKoor)
        itemView.tv_sie_kuota_detail.setText(sie.kuota)
        itemView.tv_sie_line_detail.setText(sie.idLineKoor)
        itemView.tv_sie_name_detail.setText(sie.sie)
        itemView.btn_join.setOnClickListener { listener(sie) }
    }
}