package com.me.cl.letschat.ui.main

import javax.inject.Inject

/**
 * Created by CL on 3/8/18.
 */
class MainPresenterImpl @Inject constructor(var interactor: MainInteractor):MainPresenter{
    var view:MainView?=null

    override fun handleInit(){
        finishNoBle()
        finishNoBlueTooth()
    }

    fun finishNoBle(){

    }

    fun finishNoBlueTooth(){

    }
}