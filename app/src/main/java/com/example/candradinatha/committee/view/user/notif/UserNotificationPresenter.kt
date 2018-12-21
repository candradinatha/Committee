package com.example.candradinatha.committee.view.user.notif

import android.content.ContentValues
import android.util.Log
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.NotificationResponse
import com.example.candradinatha.committee.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

class UserNotificationPresenter(val mView: UserNotificationContract.View, val apiService: ApiInterface, val schedulers: SchedulersProvider): UserNotificationContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getNotif(idMahasiswa: String) {
        mView.showloading()
        val disposable: Disposable
        disposable = apiService.getNotificaation(idMahasiswa)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(object : ResourceSubscriber<NotificationResponse>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: NotificationResponse?) {
                        mView.hideLoading()
                        t?.pemberitahuan?.let { mView.showNotif(it) }
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        Log.d(ContentValues.TAG, "error lagi"+t)
                    }

                })
        compositeDisposable.addAll(disposable)
    }
}