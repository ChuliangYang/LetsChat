package com.me.cl.letschat.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by CL on 3/8/18.
 */
interface BaseView {
}

interface BasePresenter<V> {

    val disposables: CompositeDisposable?

    /**
     * Contains common setup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    fun start() {}

    /**
     * Contains common cleanup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    fun stop() {
        disposables?.clear()
    }

    fun setView(view:V)

    fun addDisposable(disposable: Disposable) {
        disposables?.add(disposable)
    }
}
interface BaseInteractor {
}

