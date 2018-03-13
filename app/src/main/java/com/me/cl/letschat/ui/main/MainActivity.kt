package com.me.cl.letschat.ui.main

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.me.cl.letschat.R
import com.me.cl.letschat.adapter.recyclerview.DiscoverDevicesAdapter
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.main.base.MainPresenter
import com.me.cl.letschat.ui.main.base.MainView
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {
    //TODO:检查这句是否可以注入
    @Inject
    var mBluetoothAdapter: BluetoothAdapter?=null

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var discoverDevicesAdapter: DiscoverDevicesAdapter

    @BindView(R.id.rv_devices)
    lateinit var deviceList:RecyclerView

     var mScanning: Boolean = false

    // Device scan callback.
    val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        runOnUiThread {
            discoverDevicesAdapter.addNewDevice(device)
            discoverDevicesAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
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

    override fun startDiscover(){
        mScanning = true
        mBluetoothAdapter?.startLeScan(mLeScanCallback)
    }

    override fun stopDiscover(){
        mScanning = false
        mBluetoothAdapter.stopLeScan(mLeScanCallback)
    }
    override fun finishSelf(){
        finish()
    }



}
