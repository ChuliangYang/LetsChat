package com.me.cl.letschat.adapter.recyclerview

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.me.cl.letschat.R
import com.me.cl.letschat.base.ClickDevicesItem
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

/**
 * Created by CL on 3/9/18.
 */
class DiscoverDevicesAdapter @Inject constructor(val context:Context?,bluetoothDevicesNullable:MutableList<BluetoothDevice>?):RecyclerView.Adapter<ViewHolder>() {

    val bluetoothDevices:MutableList<BluetoothDevice> = bluetoothDevicesNullable?: mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return BluetoothDeviceViewHolder(context?.let {
                LayoutInflater.from(it).inflate(R.layout.item_bluetooth_device,parent,false)
            })
    }

    override fun getItemCount(): Int {
        return bluetoothDevices.size
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder is BluetoothDeviceViewHolder) {
            holder.apply {
                bluetoothDevices.get(position).let {
                    tvDeviceName.text = it.name
                    tvMacAddress.text = it.address
                }
            }
        }
    }

    fun addDevice(bluetoothDevice: BluetoothDevice?){
        bluetoothDevice?.let {
            if(!bluetoothDevices.contains(bluetoothDevice)){
                bluetoothDevices.add(it)
                notifyDataSetChanged()
            }
        }
    }

    fun getDevicesModel():MutableList<BluetoothDevice>{
            return bluetoothDevices
    }

    inner class BluetoothDeviceViewHolder(itemView:View?) : ViewHolder(itemView){
            @BindView(R.id.tv_device_name)
            lateinit var tvDeviceName:TextView
            @BindView(R.id.tv_mac_address)
            lateinit var tvMacAddress:TextView

            init {
                itemView?.let {
                    ButterKnife.bind(this,it)
                    it.onClick {
                        EventBus.getDefault().postSticky(ClickDevicesItem(bluetoothDevices.get(adapterPosition),adapterPosition))
                    }
                }
            }
    }
}