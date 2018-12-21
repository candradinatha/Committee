package com.example.candradinatha.committee.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.candradinatha.committee.R
import com.example.candradinatha.committee.model.Kepanitiaan
import kotlinx.android.synthetic.main.list_applicant.view.*

class ListKepanitiaanAdapter(private val kepanitiaan: List<Kepanitiaan>, private val listener: (Kepanitiaan)-> Unit, private val deleteListener: (Kepanitiaan)-> Unit)
    : RecyclerView.Adapter<ListPanitiaViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPanitiaViewHolder {
        return ListPanitiaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_applicant, parent, false))
    }

    override fun getItemCount(): Int {
        return kepanitiaan.size
    }

    override fun onBindViewHolder(holder: ListPanitiaViewHolder, position: Int) {
        holder.binItem(kepanitiaan[position], listener, deleteListener)
    }
}

class ListPanitiaViewHolder(view: View) : RecyclerView.ViewHolder(view){
    fun binItem(kepanitiaan: Kepanitiaan, listener: (Kepanitiaan) -> Unit, deleteListener: (Kepanitiaan) -> Unit) {
        itemView.tv_applicant_name.text = kepanitiaan.namaMahasiswa
        itemView.setOnClickListener { listener(kepanitiaan) }
        if (kepanitiaan.statusPanitia == "1")
            itemView.btn_cut_applicant.setText("cut")
        else
            itemView.btn_cut_applicant.setText("uncut")
        itemView.btn_cut_applicant.setOnClickListener { deleteListener(kepanitiaan) }
    }
}