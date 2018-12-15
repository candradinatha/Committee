package com.example.candradinatha.committee.view.admin.committee.add

import com.example.candradinatha.committee.model.Sie

interface AddCommitteeContract {
    interface View {
        fun showSie(sieKegiatan: List<Sie>)
    }
    interface Presenter {
        fun getSie(idKegiatan: String)
    }
}