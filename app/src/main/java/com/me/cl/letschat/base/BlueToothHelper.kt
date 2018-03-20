package com.me.cl.letschat.base

import java.util.*

/**
 * Created by CL on 3/12/18.
 */

val SCAN_PERIOD:Long=15//second

val STATE_DISCONNECTED = 0
val STATE_CONNECTING = 1
val STATE_CONNECTED = 2


val SERVICE_UUID = UUID.fromString("795090c7-420d-4048-a24e-18e60180e23c");
val CHARACTERISTIC_READABLE_UUID = UUID.fromString("31517c58-66bf-470c-b662-e352a6c80cba");
val CHARACTERISTIC_WRITEABLE_UUID = UUID.fromString("0b89d2d4-0ea6-4141-86bb-0c5fb91ab14a");
val DESCRIPTOR_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");