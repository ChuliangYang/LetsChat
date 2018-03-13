package com.me.cl.letschat.ui.main

import com.me.cl.letschat.R
import com.me.cl.letschat.base.SCAN_PERIOD
import com.me.cl.letschat.ui.main.base.MainInteractor
import com.me.cl.letschat.ui.main.base.MainPresenter
import com.me.cl.letschat.ui.main.base.MainView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class MainPresenterImpl @Inject constructor(var interactor: MainInteractor?): MainPresenter {
    var mainView: MainView?=null

    override val disposables= CompositeDisposable()

    val REQUEST_ENABLE_BT = 1

    override fun handleInit(){
        noBleFinish()
        noBlueToothFinish()
    }

    override fun handleResume(){
        notEnableRequest()
        mainView?.run {
            initDeviceList()
            startDiscoverLimited()
        }
    }

    private fun startDiscoverLimited(){
        Single.timer(SCAN_PERIOD,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { t1, t2 ->
             mainView?.stopDiscover()
        }
        mainView?.startDiscover()
    }

    private fun notEnableRequest() {
        if (mainView?.checkBleEnable() != true) {
            mainView?.requestEnableBlueTooth(REQUEST_ENABLE_BT)
        }
    }

    override fun setView(view: MainView) {
        mainView=view
    }


    fun noBleFinish(){
        if (mainView?.checkBleEnable() != true) {
            interactor?.run {
                mainView?.showToast(getStringFromResource(R.string.ble_not_supported))
            }
            mainView?.finishSelf()
        }
    }
    fun noBlueToothFinish(){
        if (mainView?.checkBlueToothSupport() != true) {
            interactor?.run {
                mainView?.showToast(getStringFromResource(R.string.error_bluetooth_not_supported))
            }
            mainView?.finishSelf()
        }
    }
}