package com.me.cl.letschat.ui.service.base

import com.me.cl.letschat.base.BaseInteractor
import com.me.cl.letschat.base.BasePresenter
import com.me.cl.letschat.base.BaseView

/**
 * Created by CL on 3/13/18.
 */
interface ServiceView:BaseView{

}
interface ServiceInteractor:BaseInteractor{

}
interface ServicePresenter:BasePresenter<ServiceView>{

}