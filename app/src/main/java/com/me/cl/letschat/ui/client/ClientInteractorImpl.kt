package com.me.cl.letschat.ui.client

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import com.me.cl.letschat.base.CHARACTERISTIC_READABLE_UUID
import com.me.cl.letschat.base.CHARACTERISTIC_WRITEABLE_UUID
import com.me.cl.letschat.base.SERVICE_UUID
import com.me.cl.letschat.ui.client.base.ClientInteractor
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class ClientInteractorImpl @Inject constructor(val context: Context?): ClientInteractor {

    val weakCache:HashMap<String,WeakReference<Any>> = HashMap()
    val strongCache:HashMap<String,Any> = HashMap()

    override fun getStringFromResource(resId:Int):String{
       return context?.getString(resId)?:""
    }

    override fun getReadAbleCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic? {
        return gatt.getService(SERVICE_UUID)?.getCharacteristic(CHARACTERISTIC_READABLE_UUID)
    }

    override fun getWriteAbleCharacteristic(gatt: BluetoothGatt): BluetoothGattCharacteristic? {
        return gatt.getService(SERVICE_UUID)?.getCharacteristic(CHARACTERISTIC_WRITEABLE_UUID)
    }

    override fun saveToWeakCache(key: String?, value:Any?){
        if (key!=null&&value!=null){
            weakCache.put(key, WeakReference(value))
        }
    }

    override fun getFromWeakCache(key: String?):Any?{
        key?.let {
            return weakCache[key]?.get()
        }?:let {
            return null
        }
    }

    override fun saveToStrongCache(key: String?, value:Any?){
        if (key!=null&&value!=null){
            strongCache[key] = value
        }
    }

    override fun getFromStrongCache(key: String?):Any?{
        key?.let {
            return strongCache[key]
        }?:let {
            return null
        }
    }

    companion object {
        val CACHE_WRITEABLE_CHARACTER="100"
    }
}