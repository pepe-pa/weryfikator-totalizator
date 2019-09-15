package com.hackyeah.lotto.common.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

open class RxViewModel : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun onStart() {/*override if needed*/}

    fun onStop(){
        disposable.dispose()
    }

    protected fun Disposable.register() = addTo(disposable)
}