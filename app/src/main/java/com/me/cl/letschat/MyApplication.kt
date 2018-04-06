package com.me.cl.letschat

import android.app.Application
import timber.log.Timber

/**
 * Created by CL on 3/8/18.
 */
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}