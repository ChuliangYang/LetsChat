package com.me.cl.letschat.ui.main

import android.os.Bundle
import android.widget.LinearLayout
import com.me.cl.letschat.R
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.ui.client.ClientActivity
import com.me.cl.letschat.ui.service.ServiceActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by CL on 3/15/18.
 */
class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }
}


class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            linearLayout {
                orientation = LinearLayout.VERTICAL

                button(R.string.server).lparams(width = wrapContent, height = wrapContent) {
                    bottomMargin = dip(50)

                }.onClick {
                    startActivity<ServiceActivity>()
                }

                button(R.string.client).lparams(width = wrapContent, height = wrapContent)
                        .onClick {
                            startActivity<ClientActivity>()
                        }
            }.lparams(width = wrapContent, height = wrapContent) {
                centerInParent()
            }
        }
    }
}