package com.example.candradinatha.committee.view.admin.approve.listapplicant

import android.content.ContentValues
import android.util.Log
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.KepanitiaanResponse
import com.example.candradinatha.committee.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

class ListApplicantPresenter(val mView: ListApplicantContract.View, val apiService: ApiInterface, val schedulers: SchedulersProvider): ListApplicantContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getUser(idSieKegiatan: String) {
        mView.showLoading()
        val disposable: Disposable
        disposable = apiService.getKepanitiaan(idSieKegiatan)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(object : ResourceSubscriber<KepanitiaanResponse>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: KepanitiaanResponse?) {
                        mView.hideLoading()
                        t?.kepanitiaan?.let { mView.showUser(it) }
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        Log.d(ContentValues.TAG, "error lagi"+t)
                    }

                })
    }

}