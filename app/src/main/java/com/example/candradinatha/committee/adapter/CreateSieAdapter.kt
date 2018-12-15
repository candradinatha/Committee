package com.example.candradinatha.committee.adapter

import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Sie
import kotlinx.android.synthetic.main.list_item_sie.view.*
import android.content.DialogInterface
import com.example.candradinatha.committee.api.ApiClient
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.DeleteSieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateSieAdapter(private val sieKegiatan: List<Sie>, private val listener: (Sie)-> Unit, private val deleteListener: (Sie)-> Unit)
    :RecyclerView.Adapter<SieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SieViewHolder {
        return SieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_sie, parent, false))
    }

    override fun getItemCount(): Int {
        return sieKegiatan.size
    }

    override fun onBindViewHolder(holder: SieViewHolder, position: Int) {
        holder.bindItem(sieKegiatan[position], listener, deleteListener)
    }
}

class SieViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(sie: Sie, listener: (Sie) -> Unit, deleteListener: (Sie) -> Unit) {

        itemView.tv_sie_name.text = sie.sie
        itemView.setOnClickListener { listener(sie) }
        itemView.btn_delete_sie.setOnClickListener { deleteListener(sie)}
    }

}