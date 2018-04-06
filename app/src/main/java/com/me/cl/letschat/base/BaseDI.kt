package com.me.cl.letschat.base

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.client.base.ClientComponent
import com.me.cl.letschat.ui.client.base.ClientModule
import com.me.cl.letschat.ui.service.base.ServiceComponent
import com.me.cl.letschat.ui.service.base.ServiceModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

/**
 * Created by CL on 3/8/18.
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class BlueToothActivityScope

@Component(modules = arrayOf(BlueToothActivityModule::class))
@BlueToothActivityScope
interface BlueToothComponent{
    fun plus(clientModule: ClientModule): ClientComponent
    fun plus(serviceModule: ServiceModule): ServiceComponent
}

@Module
class BlueToothActivityModule(val activity: BaseActivity?){

    @Provides
    @BlueToothActivityScope
    fun provideBlueToothAdapter(): BluetoothAdapter?{
        return (activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    @Provides
    fun provideContext(): Context?{
        return activity
    }
}