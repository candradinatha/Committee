package com.example.candradinatha.committee.view.user.mycommittee

import com.example.candradinatha.committee.model.Kepanitiaan

interface MyCommiteeContract {
    interface View {
        fun showMyCommittee(kepanitiaan: List<Kepanitiaan>)
        fun showloading()
        fun hideLoading()
    }
    interface Presenter {
        fun getMyCommittee(stsKegiatan: String, idMahasiswa: String)
    }
}