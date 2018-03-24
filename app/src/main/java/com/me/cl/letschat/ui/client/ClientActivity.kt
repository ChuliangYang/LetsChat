package com.me.cl.letschat.ui.client

import android.bluetooth.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.me.cl.letschat.R
import com.me.cl.letschat.adapter.recyclerview.DiscoverDevicesAdapter
import com.me.cl.letschat.base.*
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.chat.ChatActivity
import com.me.cl.letschat.ui.client.base.ClientPresenter
import com.me.cl.letschat.ui.client.base.ClientView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import javax.inject.Inject



class ClientActivity : BaseActivity(), ClientView {
    //TODO:检查这句是否可以注入
    @Inject
    var mBluetoothAdapter: BluetoothAdapter?=null

    @Inject
    lateinit var presenter: ClientPresenter

    @Inject
    lateinit var discoverDevicesAdapter: DiscoverDevicesAdapter

    @BindView(R.id.rv_devices)
    lateinit var deviceList:RecyclerView

    var mBluetoothGatt: BluetoothGatt?=null

     var mScanning: Boolean = false
     var mConnectionState = STATE_DISCONNECTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        EventBus.getDefault().register(this)
        presenter.setView(this)
        presenter.handleInit()
    }


    override fun onResume() {
        super.onResume()
        presenter.handleResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.handleActivityResult(requestCode, resultCode, data)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDevicesClick(event:ClickDevicesItem) {
        presenter.handleDevicesClick(event)
        EventBus.getDefault().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSendMessage(event:SendMessageUseBle) {
        presenter.handleSendMessage(event)
        EventBus.getDefault().removeStickyEvent(event)
    }

    override fun showToast(message: String) {
         makeToast(message)
    }

    override fun checkBleEnable():Boolean{
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    override fun checkBlueToothSupport():Boolean{
       return mBluetoothAdapter != null
    }

    override fun checkBlueToothEnabled():Boolean{
        return mBluetoothAdapter?.isEnabled == true
    }

    override fun requestEnableBlueTooth(requestCode: Int){
        startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), requestCode)
    }

    override fun initDeviceList(){
        deviceList.adapter=discoverDevicesAdapter
    }

    // Device scan callback.
    val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        runOnUiThread {
            discoverDevicesAdapter.addNewDevice(device)
            discoverDevicesAdapter.notifyDataSetChanged()
        }
    }

    override fun startDiscover(){
        mScanning = true
        mBluetoothAdapter?.startLeScan(mLeScanCallback)
    }

    override fun stopDiscover(){
        mScanning = false
        mBluetoothAdapter?.stopLeScan(mLeScanCallback)
    }
    override fun finishSelf(){
        finish()
    }


    val mGattCallback: BluetoothGattCallback = object: BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int){
                presenter.handleConnectionStateChange(gatt, status, newState)
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                presenter.handleServicesDiscovered(gatt, status)
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {

        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
                presenter.handleCharacteristicChanged(gatt, characteristic)
        }
    }


    override fun connectToGattServer(device:BluetoothDevice?){
        mBluetoothGatt = device?.connectGatt(this, false, mGattCallback)
    }

    override fun maintainConnectState(state:Int){
        mConnectionState = state
    }

    override fun discoverServices(){
        mBluetoothGatt?.discoverServices()
    }

    override fun setCharacteristicNotification(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic?, enable: Boolean) {
        characteristic?.let {
            gatt.setCharacteristicNotification(it, enable)
        }
    }

    override fun writeCharacteristic(characteristic: BluetoothGattCharacteristic?,message: String?):Boolean{
        characteristic?.setValue(message)
        return mBluetoothGatt?.writeCharacteristic(characteristic)?:false
    }

    override fun startChatActivity(){
        startActivity<ChatActivity>(INTENT_EXTRA_BLE_DIRECTION to FROM_CLIENT)
    }

}
