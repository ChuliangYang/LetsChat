package com.me.cl.letschat.ui.client

import android.app.Activity
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothProfile
import android.content.Intent
import com.me.cl.letschat.R
import com.me.cl.letschat.base.ClickDevicesItem
import com.me.cl.letschat.base.SCAN_PERIOD
import com.me.cl.letschat.base.STATE_CONNECTED
import com.me.cl.letschat.base.STATE_DISCONNECTED
import com.me.cl.letschat.ui.client.base.MainInteractor
import com.me.cl.letschat.ui.client.base.MainPresenter
import com.me.cl.letschat.ui.client.base.MainView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class ClientPresenterImpl @Inject constructor(var interactor: MainInteractor?): MainPresenter {
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

    override fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            mainView?.finishSelf()
        }
    }

    override fun handleDevicesClick(event: ClickDevicesItem){
        event.currentDevice?.let {
            mainView?.connectToGattServer(it)
        }
    }

    override fun handleConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int){
        if (newState== BluetoothProfile.STATE_CONNECTED) {
                mainView?.apply {
                    maintainConnectState(STATE_CONNECTED)
                    discoverServices()
                }
        }else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mainView?.apply {
                    maintainConnectState(STATE_DISCONNECTED)
                }
        }
    }

    private fun startDiscoverLimited(){
        addDisposable(Single.timer(SCAN_PERIOD,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { t1, t2 ->
            mainView?.stopDiscover()
        })
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