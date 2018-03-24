package com.me.cl.letschat.adapter.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.me.cl.letschat.R
import com.me.cl.letschat.bean.ChatBean
import com.me.cl.letschat.bean.DIRECTION_RECEIVE
import com.me.cl.letschat.bean.DIRECTION_SEND

/**
 * Created by CL on 3/20/18.
 */
class ChatAdapter(val context:Context?,val chatList:MutableList<ChatBean>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
       return ViewHolderChat(LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false))
    }

    override fun getItemCount(): Int {
       return chatList?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ViewHolderChat){
            if(chatList?.get(position)?.direction== DIRECTION_SEND){
                holder.apply {
                    tvMessageSend.visibility=View.VISIBLE
                    tvMessageReceive.visibility=View.GONE
                    tvMessageSend.text=chatList.get(position).message
                }
            }else if (chatList?.get(position)?.direction== DIRECTION_RECEIVE){
                holder.apply {
                    tvMessageReceive.visibility=View.VISIBLE
                    tvMessageSend.visibility=View.GONE
                    tvMessageReceive.text=chatList.get(position).message
                }
            }
        }
    }

    fun addChat(chatBean:ChatBean?){
        chatBean?.let {
            chatList?.add(it)
        }
    }


    inner class ViewHolderChat(itemview:View?):RecyclerView.ViewHolder(itemview){
            @BindView(R.id.tv_message_receive)
            lateinit var tvMessageReceive:TextView

            @BindView(R.id.tv_message_send)
            lateinit var tvMessageSend:TextView

            init {
                    itemview?.let {
                        ButterKnife.bind(this,it)
                    }
            }
    }
}