package com.me.cl.letschat.ui.service

import android.bluetooth.*
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.me.cl.letschat.R
import com.me.cl.letschat.base.*
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.chat.ChatActivity
import com.me.cl.letschat.ui.service.base.ServiceModule
import com.me.cl.letschat.ui.service.base.ServicePresenter
import com.me.cl.letschat.ui.service.base.ServiceView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider


/**
 * Created by CL on 3/13/18.
 */
class ServiceActivity:BaseActivity(),ServiceView {

    @Inject
    lateinit var mBluetoothAdapterProvider: Provider<BluetoothAdapter?>

    @Inject
    lateinit var presenter: ServicePresenter

    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerBlueToothComponent.builder().blueToothActivityModule(BlueToothActivityModule(this)).build()
                .plus(ServiceModule()).inject(this)
        setContentView(R.layout.activity_service)
        ButterKnife.bind(this)
        presenter.setView(this)
        presenter.handleInit()

    }

    override fun checkBleEnable():Boolean{
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    override fun showToast(message: String) {
        makeToast(message)
    }

    override fun checkBlueToothSupport():Boolean{
        return mBluetoothAdapterProvider.get() != null
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mBluetoothAdapterProvider.get()?.bluetoothLeAdvertiser?.startAdvertising(settings, data, mAdvertiseCallback)
        }else{
                finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSendMessage(event: SendMessageUseBle) {
        presenter?.handleSendMessage(event)
        EventBus.getDefault().removeStickyEvent(event)
    }
    //todo 收到消息未处理
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

    override fun startChatActivity(){
        startActivity<ChatActivity>(INTENT_EXTRA_BLE_DIRECTION to FROM_SERVER)
    }

    //todo 未知是否合法
    override fun writeCharacteristic(characteristic: BluetoothGattCharacteristic?,message: String?):Boolean{
        characteristic?.setValue(message)
        return true
    }

}