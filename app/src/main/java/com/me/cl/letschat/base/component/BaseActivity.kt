package com.me.cl.letschat.base.component

import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.toast

/**
 * Created by CL on 3/6/18.
 */
open class BaseActivity: AppCompatActivity() {
    fun makeToast(message:String){
        toast(message)
    }
}