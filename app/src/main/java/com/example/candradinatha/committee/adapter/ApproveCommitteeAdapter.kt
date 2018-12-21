package com.example.candradinatha.committee.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Committee
import kotlinx.android.synthetic.main.list_item_approve.view.*

class ApproveCommitteeAdapter(private val committee: List<Committee>, private val listener: (Committee) -> Unit)
    :RecyclerView.Adapter<ApproveCommitteeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproveCommitteeViewHolder {
        return ApproveCommitteeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_approve, parent, false))
    }

    override fun getItemCount(): Int {
        return committee.size
    }

    override fun onBindViewHolder(holder: ApproveCommitteeViewHolder, position: Int) {
        holder.bindItem(committee[position], listener)
    }
}

class ApproveCommitteeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(committee: Committee, listener: (Committee) -> Unit){
        Glide.with(itemView.context).load(committee.fotoKegiatan).into(itemView.iv_committee_approve)
        itemView.tv_approve_committee.setText(committee.namaKegiatan)
        itemView.tv_date_committee_approve.setText(committee.tglKegiatan)
        itemView.setOnClickListener { listener(committee) }
    }
}
