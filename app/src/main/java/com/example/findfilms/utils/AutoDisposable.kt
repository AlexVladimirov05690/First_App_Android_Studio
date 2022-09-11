package com.example.findfilms.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.example.findfilms.R
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class AutoDisposable : LifecycleObserver, DefaultLifecycleObserver {
    lateinit var compositeDisposable: CompositeDisposable
    fun bindTo(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
    }

    fun add(disposable: Disposable) {
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.add(disposable)
        } else {
            throw NotImplementedError(R.string.not_implemented_error.toString())
        }
    }

    @Override
    fun onDestroy(lifecycle: Lifecycle) {
        compositeDisposable.dispose()
    }
}

fun Disposable.addTo(autoDisposable: AutoDisposable) {
    autoDisposable.add(this)
}