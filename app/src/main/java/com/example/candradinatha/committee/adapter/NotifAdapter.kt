package com.example.candradinatha.committee.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Notification
import kotlinx.android.synthetic.main.list_notif.view.*

class NotifAdapter(private val notif: List<Notification>, private val listener: (Notification) -> Unit)
    : RecyclerView.Adapter<NotifViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        return NotifViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_notif, parent, false))
    }

    override fun getItemCount(): Int {
        return notif.size
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        holder.bindItem(notif[position],listener)
    }
}

class NotifViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(notif: Notification, listener: (Notification) -> Unit) {
        itemView.tv_notif_judul.setText(notif.judul)
        itemView.tv_notif_coontent.setText(notif.pesan)
        itemView.setOnClickListener { listener(notif) }
    }
}