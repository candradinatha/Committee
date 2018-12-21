package com.example.candradinatha.committee.view.admin.approve.listsie

import android.content.ContentValues
import android.util.Log
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.SieResponse
import com.example.candradinatha.committee.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

class ListSiePresenter(val mView: ListSieContract.View, val apiService: ApiInterface, val schedulers: SchedulersProvider): ListSieContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getSie(idKegiatan: String) {
        mView.showLoading()
        val disposable: Disposable
        disposable = apiService.getSie(idKegiatan)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(object : ResourceSubscriber<SieResponse>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: SieResponse?) {
                        mView.hideLoading()
                        t?.sieKegiatan?.let { mView.showSie(it) }
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        Log.d(ContentValues.TAG, "error lagi"+t)
                    }
                })
        compositeDisposable.addAll(disposable)
    }
}