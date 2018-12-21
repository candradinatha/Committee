package com.example.candradinatha.committee.view.user.notif

import com.example.candradinatha.committee.model.Notification


interface UserNotificationContract {
    interface View {
        fun showNotif(pemberitahuan: List<Notification>)
        fun showloading()
        fun hideLoading()
    }
    interface Presenter {
        fun getNotif(idMahasiswa: String)
    }
}