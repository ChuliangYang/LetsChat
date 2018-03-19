package com.me.cl.letschat.ui.service

import android.bluetooth.*
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.pm.PackageManager
import android.os.Bundle
import com.me.cl.letschat.R
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.service.base.ServicePresenter
import com.me.cl.letschat.ui.service.base.ServiceView
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by CL on 3/13/18.
 */
class ServiceActivity:BaseActivity(),ServiceView {

    //TODO:检查这句是否可以注入
    @Inject
    var mBluetoothAdapter: BluetoothAdapter?=null

    @Inject
    lateinit var presenter: ServicePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        presenter.handleInit()

    }

    override fun checkBleEnable():Boolean{
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    override fun showToast(message: String) {
        makeToast(message)
    }

    override fun checkBlueToothSupport():Boolean{
        return mBluetoothAdapter != null
    }

    override fun finishSelf(){
        finish()
    }

     val mAdvertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
            Timber.d("LE Advertise Started.")
        }

        override fun onStartFailure(errorCode: Int) {
            Timber.d("LE Advertise Failed: " + errorCode)
        }
    }

    override fun startAdvertise(settings: AdvertiseSettings,data: AdvertiseData){
        mBluetoothAdapter?.bluetoothLeAdvertiser?.startAdvertising(settings, data, mAdvertiseCallback)
    }

    val bluetoothGattServerCallback:BluetoothGattServerCallback =object: BluetoothGattServerCallback() {
        override fun onCharacteristicWriteRequest(device: BluetoothDevice?, requestId: Int, characteristic: BluetoothGattCharacteristic?, preparedWrite: Boolean, responseNeeded: Boolean, offset: Int, value: ByteArray?) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value)
        }

        override fun onCharacteristicReadRequest(device: BluetoothDevice?, requestId: Int, offset: Int, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic)
        }

        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
        }

    }

    override fun startGattServer(service: BluetoothGattService?){
        (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).openGattServer(this, bluetoothGattServerCallback)
                .addService(service)
    }

}