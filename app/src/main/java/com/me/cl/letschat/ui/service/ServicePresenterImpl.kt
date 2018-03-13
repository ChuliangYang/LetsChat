package com.me.cl.letschat.ui.service

import com.me.cl.letschat.ui.service.base.ServicePresenter
import com.me.cl.letschat.ui.service.base.ServiceView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by CL on 3/13/18.
 */
class ServicePresenterImpl:ServicePresenter {

    override val disposables=CompositeDisposable()
    lateinit var serviceView:ServiceView


    override fun setView(view: ServiceView) {
        serviceView=view
    }
}