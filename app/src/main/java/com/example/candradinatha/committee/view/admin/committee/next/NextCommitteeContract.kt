package com.example.candradinatha.committee.view.admin.committee.next

import com.example.candradinatha.committee.model.Committee

interface NextCommitteeContract {
    interface View {
        fun showPrevCommittee(kegiatan: List<Committee>)
        fun showloading()
        fun hideLoading()
    }
    interface Presenter {
        fun getCommittee(sts: String)
    }
}