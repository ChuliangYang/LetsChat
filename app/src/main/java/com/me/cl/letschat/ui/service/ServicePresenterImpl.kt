package com.me.cl.letschat.ui.service

import com.me.cl.letschat.R
import com.me.cl.letschat.ui.service.base.ServiceInteractor
import com.me.cl.letschat.ui.service.base.ServicePresenter
import com.me.cl.letschat.ui.service.base.ServiceView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by CL on 3/13/18.
 */
class ServicePresenterImpl @Inject constructor(val interactor: ServiceInteractor?):ServicePresenter {

    override val disposables=CompositeDisposable()
    var serviceView:ServiceView?=null


    override fun setView(view: ServiceView) {
        serviceView=view
    }

    override fun handleInit(){
        noBleFinish()
        noBlueToothFinish()
        startAdvertising()
        createGattServer()
    }

    private fun createGattServer() {
        interactor?.run {
            serviceView?.startGattServer(interactor.createService())
        }
    }

    fun startAdvertising() {
        interactor?.run {
            serviceView?.startAdvertise(interactor.buildAdvertiseSettings(),interactor.buildAdvertiseData())
        }
    }

    fun noBleFinish(){
        if (serviceView?.checkBleEnable() != true) {
            interactor?.run {
                serviceView?.showToast(getStringFromResource(R.string.ble_not_supported))
            }
            serviceView?.finishSelf()
        }
    }
    fun noBlueToothFinish(){
        if (serviceView?.checkBlueToothSupport() != true) {
            interactor?.run {
                serviceView?.showToast(getStringFromResource(R.string.error_bluetooth_not_supported))
            }
            serviceView?.finishSelf()
        }
    }
}