package com.me.cl.letschat.ui.service

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import com.me.cl.letschat.R
import com.me.cl.letschat.base.AddNewChat
import com.me.cl.letschat.base.DIRECTION_SEND
import com.me.cl.letschat.base.FROM_SERVER
import com.me.cl.letschat.base.SendMessageUseBle
import com.me.cl.letschat.ui.service.ServiceInteractorImpl.Companion.CACHE_READABLE_CHARACTER
import com.me.cl.letschat.ui.service.base.ServiceInteractor
import com.me.cl.letschat.ui.service.base.ServicePresenter
import com.me.cl.letschat.ui.service.base.ServiceView
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
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

    override fun handleConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int){
            if (newState==STATE_CONNECTED){
                serviceView?.startChatActivity()
            }
    }

    override fun handleSendMessage(event: SendMessageUseBle){
        if (event.fromWhere== FROM_SERVER) {
            interactor?.let {
                val readableCharacter = it.getFromCache(CACHE_READABLE_CHARACTER)
                if (readableCharacter is BluetoothGattCharacteristic) {
                    if (serviceView?.writeCharacteristic(readableCharacter, event.message) == true) {
                        EventBus.getDefault().postSticky(AddNewChat(DIRECTION_SEND, event.message))
                    }
                }
            }
        }
    }


    private fun createGattServer() {
        interactor?.run {
            val gattService=createService()
            serviceView?.startGattServer(gattService)
            saveReadableCharacteristic(gattService)
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