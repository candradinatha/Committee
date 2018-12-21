package com.example.candradinatha.committee.view.user.mycommittee

import com.example.candradinatha.committee.model.Committee

interface MyCommiteeContract {
    interface View {
        fun showCommittee(kegiatan: List<Committee>)
        fun showloading()
        fun hideLoading()
    }
    interface Presenter {
        fun getCommittee(sts: String)
    }
}