package com.example.candradinatha.committee.view.admin.approve.listsie

import com.example.candradinatha.committee.model.Committee
import com.example.candradinatha.committee.model.Sie

interface ListSieContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showSie(sieKegiatan: List<Sie>)
    }
    interface Presenter {
        fun getSie(idKegiatan: String)
    }
}