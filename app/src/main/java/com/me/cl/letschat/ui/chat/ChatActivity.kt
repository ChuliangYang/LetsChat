package com.me.cl.letschat.ui.chat


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.Gravity
import com.me.cl.letschat.R
import com.me.cl.letschat.adapter.recyclerview.ChatAdapter
import com.me.cl.letschat.base.AddNewChat
import com.me.cl.letschat.base.INTENT_EXTRA_BLE_DIRECTION
import com.me.cl.letschat.base.SendMessageUseBle
import com.me.cl.letschat.base.component.BaseActivity
import com.me.cl.letschat.bean.ChatBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

class ChatActivity : BaseActivity() {
    @Inject
    lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        relativeLayout {
            recyclerView().lparams(width = matchParent, height = matchParent) {
                above(R.id.rl_message)
            }.apply {
                layoutManager=LinearLayoutManager(context,VERTICAL,false)
                adapter=chatAdapter
            }

            relativeLayout {
                id = Ids.rl_message
                leftPadding=dip(5)
                rightPadding=dip(5)

                val editText=editText().lparams(width = matchParent, height = wrapContent) {
                    leftOf(R.id.btn_send)
                }

                button(R.string.send) {
                    gravity = Gravity.CENTER
                    id = Ids.btn_send
                }.lparams(width = wrapContent, height = wrapContent) {
                    alignParentEnd()
                    centerVertically()
                }.onClick {
                    if (editText.text.isNullOrEmpty()){
                        makeToast(getString(R.string.message_empty_warn))
                    }else{
                        EventBus.getDefault().postSticky(SendMessageUseBle(intent.getIntExtra(INTENT_EXTRA_BLE_DIRECTION,0),editText.text.toString()))
                    }
                }

            }.lparams(width = matchParent, height = wrapContent) {
                alignParentBottom()
            }
        }
    }

    private object Ids {
        val btn_send = 1
        val rl_message = 2
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefreshChatList(event: AddNewChat){
        chatAdapter.addChat(ChatBean(event.direction,event.message))
        chatAdapter.notifyDataSetChanged()
        EventBus.getDefault().removeStickyEvent(event)
    }
}