package com.example.candradinatha.committee.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Kepanitiaan
import kotlinx.android.synthetic.main.committee_list.view.*

class MyCommitteeAdapter(private val kepanitiaan: List<Kepanitiaan>, private val listener: (Kepanitiaan) -> Unit)
    : RecyclerView.Adapter<MyCommitteeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommitteeViewHolder {
        return MyCommitteeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.committee_list, parent, false))
    }

    override fun getItemCount(): Int {
        return kepanitiaan.size
    }

    override fun onBindViewHolder(holder: MyCommitteeViewHolder, position: Int) {
        holder.bindItem(kepanitiaan[position], listener)
    }

}

class MyCommitteeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(kepanitiaan: Kepanitiaan, listener: (Kepanitiaan) -> Unit) {
        itemView.tv_committee_name.text = kepanitiaan.namaKegiatan
        Glide.with(itemView.context).load(kepanitiaan.fotoKegiatan).into(itemView.iv_committee)
        itemView.setOnClickListener { listener(kepanitiaan) }
    }
}