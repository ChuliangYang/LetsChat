package com.me.cl.letschat.ui.client

import android.content.Context
import com.me.cl.letschat.ui.client.base.MainInteractor
import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class ClientInteractorImpl @Inject constructor(val context: Context?): MainInteractor {
    override fun getStringFromResource(resId:Int):String{
       return context?.getString(resId)?:""
    }
}