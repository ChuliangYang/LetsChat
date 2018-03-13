package com.me.cl.letschat.ui.main.base

import com.me.cl.letschat.base.BaseInteractor
import com.me.cl.letschat.base.BasePresenter
import com.me.cl.letschat.base.BaseView

/**
 * Created by CL on 3/8/18.
 */
interface MainView: BaseView{
    fun showToast(message:String)
    fun checkBleEnable(): Boolean
    fun checkBlueToothSupport(): Boolean
    fun finishSelf()
    fun checkBlueToothEnabled(): Boolean
    fun requestEnableBlueTooth(requestCode: Int)
    fun initDeviceList()
    fun startDiscover()
    fun stopDiscover()
}
interface MainPresenter: BasePresenter<MainView> {
    fun handleInit()
    fun handleResume()
}
interface MainInteractor: BaseInteractor{

    fun getStringFromResource(resId: Int): String
}