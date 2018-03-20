package com.me.cl.letschat.ui.service

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.*
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothGattService.SERVICE_TYPE_PRIMARY
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import com.me.cl.letschat.base.CHARACTERISTIC_READABLE_UUID
import com.me.cl.letschat.base.CHARACTERISTIC_WRITEABLE_UUID
import com.me.cl.letschat.base.SERVICE_UUID
import com.me.cl.letschat.ui.service.base.ServiceInteractor
import javax.inject.Inject


/**
 * Created by CL on 3/13/18.
 */
class ServiceInteractorImpl @Inject constructor(val context: Context?):ServiceInteractor {

    override fun getStringFromResource(resId:Int):String{
        return context?.getString(resId)?:""
    }

    override fun buildAdvertiseSettings():AdvertiseSettings{
        return AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .build()
    }

    override fun buildAdvertiseData(): AdvertiseData {
        return AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(false)
                .addServiceUuid(ParcelUuid(SERVICE_UUID))
                .build()
    }

    override fun createService(): BluetoothGattService {
        return BluetoothGattService(SERVICE_UUID, SERVICE_TYPE_PRIMARY).apply {
            // Counter characteristic (read-only, supports subscriptions)
            val readable = BluetoothGattCharacteristic(CHARACTERISTIC_READABLE_UUID, PROPERTY_READ or PROPERTY_NOTIFY, PERMISSION_READ)
            // Interactor characteristic
            val writeable = BluetoothGattCharacteristic(CHARACTERISTIC_WRITEABLE_UUID, PROPERTY_WRITE_NO_RESPONSE, PERMISSION_WRITE)
            addCharacteristic(readable)
            addCharacteristic(writeable)
        }
    }
}