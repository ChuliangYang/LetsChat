package com.me.cl.letschat.ui.service.base

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import com.me.cl.letschat.base.BaseInteractor
import com.me.cl.letschat.base.BasePresenter
import com.me.cl.letschat.base.BaseView
import com.me.cl.letschat.base.SendMessageUseBle

/**
 * Created by CL on 3/13/18.
 */
interface ServiceView:BaseView{
    fun checkBleEnable(): Boolean
    fun showToast(message: String)
    fun checkBlueToothSupport(): Boolean
    fun finishSelf()
    fun startAdvertise(settings: AdvertiseSettings, data: AdvertiseData)
    fun startGattServer(service: BluetoothGattService?)
    fun startChatActivity()
    fun writeCharacteristic(characteristic: BluetoothGattCharacteristic?, message: String?): Boolean
}
interface ServiceInteractor:BaseInteractor{
    fun getStringFromResource(resId: Int): String
    fun buildAdvertiseData(): AdvertiseData
    fun buildAdvertiseSettings(): AdvertiseSettings
    fun createService(): BluetoothGattService
    fun saveReadableCharacteristic(bluetoothGattService: BluetoothGattService?): Boolean
    fun getFromCache(key: String?): Any?
}
interface ServicePresenter:BasePresenter<ServiceView>{

    fun handleInit()
    fun handleConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int)
    fun handleSendMessage(event: SendMessageUseBle)
}