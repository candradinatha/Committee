package com.example.candradinatha.committee.view.user.mycommittee

import android.content.ContentValues
import android.util.Log
import com.example.candradinatha.committee.api.ApiInterface
import com.example.candradinatha.committee.model.CommitteeResponse
import com.example.candradinatha.committee.model.KepanitiaanResponse
import com.example.candradinatha.committee.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.ResourceSubscriber

class MyCommitteePresenter(val mView: MyCommiteeContract.View, val apiService: ApiInterface, val schedulers: SchedulersProvider): MyCommiteeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getMyCommittee(stsKegiatan: String, idMahasiswa: String) {
        mView.showloading()
        val disposable: Disposable
        disposable = apiService.getMyCommittee(stsKegiatan, idMahasiswa)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(object : ResourceSubscriber<KepanitiaanResponse>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: KepanitiaanResponse?) {
                        mView.hideLoading()
                        t?.kepanitiaan?.let { mView.showMyCommittee(it) }
                    }

                    override fun onError(t: Throwable?) {
                        mView.hideLoading()
                        Log.d(ContentValues.TAG, "error lagi"+t)
                    }

                })
        compositeDisposable.addAll(disposable)
    }

}