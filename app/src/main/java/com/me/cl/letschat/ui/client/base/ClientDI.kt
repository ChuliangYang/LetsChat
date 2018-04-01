package com.me.cl.letschat.ui.client.base

import android.content.Context
import com.me.cl.letschat.adapter.recyclerview.DiscoverDevicesAdapter
import com.me.cl.letschat.ui.client.ClientActivity
import com.me.cl.letschat.ui.client.ClientInteractorImpl
import com.me.cl.letschat.ui.client.ClientPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by CL on 3/8/18.
 */
@Subcomponent(modules= arrayOf(ClientModule::class))
interface ClientComponent{
    fun inject(clientActivity: ClientActivity)
}



@Module
class ClientModule(){
    @Provides
    fun provideClientPresenter(clientPresenterImpl: ClientPresenterImpl):ClientPresenter{
        return clientPresenterImpl
    }

    @Provides
    fun provideClientInteractor(clientInteractorImpl: ClientInteractorImpl):ClientInteractor{
        return clientInteractorImpl
    }

    @Provides
    fun provideDiscoverDevicesAdapter(context: Context?): DiscoverDevicesAdapter {
        return DiscoverDevicesAdapter(context,null)
    }
}