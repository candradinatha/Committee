package com.example.candradinatha.committee.view.admin.committee.add

import android.content.ContentValues
import android.util.Log
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.SieResponse
import com.example.candradinatha.committee.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

class AddCommitteePresenter(val mView: AddCommitteeContract.View, val apiService: ApiInterface, val schedulers: SchedulersProvider): AddCommitteeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getSie(idKegiatan: String) {
        val disposable: Disposable
        disposable =apiService.getSie(idKegiatan)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(object : ResourceSubscriber<SieResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: SieResponse?) {
                        t?.sieKegiatan?.let{mView.showSie(it)}
                    }

                    override fun onError(t: Throwable?) {
                        Log.d(ContentValues.TAG, "error lagi"+t)
                    }

                })
        compositeDisposable.addAll(disposable)
    }

}