package com.me.cl.letschat.ui.service.base

import com.me.cl.letschat.ui.service.ServiceActivity
import com.me.cl.letschat.ui.service.ServiceInteractorImpl
import com.me.cl.letschat.ui.service.ServicePresenterImpl
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by CL on 3/13/18.
 */
@Subcomponent(modules= arrayOf(ServiceModule::class))
interface ServiceComponent{
    fun inject(serviceActivity: ServiceActivity)
}



@Module
class ServiceModule(){
    @Provides
    fun provideServicePresenter(servicePresenterImpl: ServicePresenterImpl):ServicePresenter{
        return servicePresenterImpl
    }

    @Provides
    fun provideServiceInteractor(serviceInteractorImpl: ServiceInteractorImpl):ServiceInteractor{
        return serviceInteractorImpl
    }
}