package com.example.candradinatha.committee.adapter

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Committee
import kotlinx.android.synthetic.main.committee_list.view.*

class PrevCommitteeAdapter(private val events: List<Committee>, private val listener: (Committee) -> Unit)
    : RecyclerView.Adapter<PrevCommitteeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevCommitteeViewHolder {
        return PrevCommitteeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.committee_list, parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: PrevCommitteeViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }
}

class PrevCommitteeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(committee: Committee, listener: (Committee) -> Unit) {
        itemView.tv_committee_name.text = committee.namaKegiatan
        Glide.with(itemView.context).load(committee.fotoKegiatan).into(itemView.iv_committee)
        itemView.setOnClickListener { listener(committee) }
    }
}