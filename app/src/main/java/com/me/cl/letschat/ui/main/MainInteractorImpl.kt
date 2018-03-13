package com.me.cl.letschat.ui.main

import android.content.Context
import com.me.cl.letschat.ui.main.base.MainInteractor
import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class MainInteractorImpl @Inject constructor(val context: Context?): MainInteractor {
    override fun getStringFromResource(resId:Int):String{
       return context?.getString(resId)?:""
    }
}