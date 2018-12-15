package com.example.candradinatha.committee.view.admin.committee.detail

import android.content.ContentValues
import android.util.Log
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.CommitteeResponse
import com.example.candradinatha.committee.model.SieResponse
import com.example.candradinatha.committee.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

class DetailCommitteePresenter(val mView: DetailCommitteeContract.View, val apiService: ApiInterface, val schedulers: SchedulersProvider): DetailCommitteeContract.Presenter {

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

    override fun getCommittee(idKegiatan: String) {
        mView.showLoading()
        val disposable: Disposable
        disposable = apiService.getCommitteeId(idKegiatan)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(object : ResourceSubscriber<CommitteeResponse>() {
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: CommitteeResponse?) {
                        mView.hideLoading()
                        t?.kegiatan?.let { mView.showCommittee(it) }
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        Log.d(ContentValues.TAG, "error lagi"+t)
                    }

                })
        compositeDisposable.addAll(disposable)
    }

}