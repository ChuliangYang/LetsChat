package com.me.cl.letschat.ui.client

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import com.me.cl.letschat.base.CHARACTERISTIC_READABLE_UUID
import com.me.cl.letschat.base.CHARACTERISTIC_WRITEABLE_UUID
import com.me.cl.letschat.base.SERVICE_UUID
import com.me.cl.letschat.ui.client.base.ClientInteractor
import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class ClientInteractorImpl @Inject constructor(val context: Context?): ClientInteractor {
    override fun getStringFromResource(resId:Int):String{
       return context?.getString(resId)?:""
    }

    override fun getReadAbleCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic? {
        return gatt.getService(SERVICE_UUID).getCharacteristic(CHARACTERISTIC_READABLE_UUID)
    }

    override fun getWriteAbleCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic? {
        return gatt.getService(SERVICE_UUID).getCharacteristic(CHARACTERISTIC_WRITEABLE_UUID)
    }
}