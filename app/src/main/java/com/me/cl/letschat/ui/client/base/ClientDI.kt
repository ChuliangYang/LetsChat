package com.me.cl.letschat.ui.client.base

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.client.ClientActivity
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by CL on 3/8/18.
 */
@Component(modules= arrayOf(MainModule::class))
interface MainComponent{
    fun inject(mainActivity: ClientActivity)
}



@Module
class MainModule(var baseActivityNullable: BaseActivity?){

    @Provides
    fun provideBluetoothAdapter(): BluetoothAdapter?{
        return (baseActivityNullable?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }
}