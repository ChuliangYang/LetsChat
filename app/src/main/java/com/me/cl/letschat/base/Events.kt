package com.me.cl.letschat.base

import android.bluetooth.BluetoothDevice
/**
 * Created by CL on 3/13/18.
 */
class ClickDevicesItem(val currentDevice:BluetoothDevice?,val position:Int?)

class SendMessageUseBle(val fromWhere:Int,val message:String)

class AddNewChat(val direction:Int,val message: String)
val DIRECTION_SEND=com.me.cl.letschat.bean.DIRECTION_SEND
val DIRECTION_RECEIVE=com.me.cl.letschat.bean.DIRECTION_RECEIVE