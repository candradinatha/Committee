package com.example.candradinatha.committee.view.admin.approve.listapplicant

import com.example.candradinatha.committee.model.Kepanitiaan

interface ListApplicantContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showUser(kepanitiaan: List<Kepanitiaan>)
    }
    interface Presenter {
        fun getUser(idSieKegiatan: String)
    }
}