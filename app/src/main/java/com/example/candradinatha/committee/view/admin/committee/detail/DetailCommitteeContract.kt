package com.example.candradinatha.committee.view.admin.committee.detail

import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.model.Sie

interface DetailCommitteeContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showSie(sieKegiatan: List<Sie>)
        fun showCommittee(kegiatan: List<Committee>)
    }
    interface Presenter {
        fun getSie(idKegiatan: String)
        fun getCommittee(idKegiatan: String)
    }
}