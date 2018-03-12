package com.me.cl.letschat.ui.main

import com.me.cl.letschat.base.BaseInteractor
import com.me.cl.letschat.base.BasePresenter
import com.me.cl.letschat.base.BaseView

/**
 * Created by CL on 3/8/18.
 */
interface MainView: BaseView{
    fun showToast(message:String)
    fun checkBleEnable(): Boolean
    fun checkBlueToothEnable(): Boolean
}
interface MainPresenter: BasePresenter{

}
interface MainInteractor: BaseInteractor{

}