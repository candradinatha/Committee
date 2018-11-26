package com.example.candradinatha.committee.view.admin.committee.prev

import com.example.candradinatha.committee.model.Committee

interface PrevCommitteeContract {
    interface View {
        fun showPrevCommittee(kegiatan: List<Committee>)
        fun showloading()
        fun hideLoading()
    }
    interface Presenter {
        fun getCommittee(sts: String)
    }
}