package com.me.cl.letschat.ui.client.base

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Intent
import com.me.cl.letschat.base.*

/**
 * Created by CL on 3/8/18.
 */
interface ClientView: BaseView{
    fun showToast(message:String)
    fun checkBleEnable(): Boolean
    fun checkBlueToothSupport(): Boolean
    fun finishSelf()
    fun checkBlueToothEnabled(): Boolean
    fun requestEnableBlueTooth(requestCode: Int)
    fun initDeviceList()
    fun startDiscover()
    fun stopDiscover()
    fun connectToGattServer(device: BluetoothDevice?)
    fun maintainConnectState(state: Int)
    fun discoverServices()
    fun setCharacteristicNotification(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic?, enable: Boolean)
    fun writeCharacteristic(characteristic: BluetoothGattCharacteristic?, message: String?): Boolean
    fun startChatActivity()
    fun showTitleProgressBar(show: Boolean)
}
interface ClientPresenter: BasePresenter<ClientView> {
    fun handleInit()
    fun handleResume()
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun handleDevicesClick(event: ClickDevicesItem)
    fun handleConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int)
    fun handleServicesDiscovered(gatt: BluetoothGatt, status: Int)
    fun handleCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?)
    fun handleSendMessage(event: SendMessageUseBle)
}
interface ClientInteractor: BaseInteractor{
    fun getStringFromResource(resId: Int): String
    fun getReadAbleCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic?
    fun getWriteAbleCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic?
    fun saveToWeakCache(key: String?, value: Any?)
    fun getFromWeakCache(key: String?): Any?
    fun saveToStrongCache(key: String?, value: Any?)
    fun getFromStrongCache(key: String?): Any?
}