package com.test.jasonchen.assemblydemo.view

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

/**
 * Created by marti on 2018/3/10.
 */
object JBus {
    val listenerMap = mutableMapOf<String, OnEventMessageListener>()
    private var c: Class<*>? = null//指定发送的对象
    private val SEND_ALL = 0  //发送所有
    private val SEND_SINGLE = 1   //发送单个


    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //收到消息给所有注册监听的对象发送消息
            when (msg.what) {
                SEND_ALL -> {
                    //群发消息
                    val keys = listenerMap.keys
                    for (key in keys) {
                        postMsg(key, msg)
                    }
                }
                SEND_SINGLE ->
                    //指定发送
                    if (c != null) {
                        postMsg(c!!.name, msg)
                        c = null
                    }
            }
        }
    }

    /**
     * POST发送MSG
     * @param key
     * @param msg
     */
    private fun postMsg(key: String, msg: Message?) {
        val listener = listenerMap[key]
        if (listener != null && msg != null && msg.obj != null) {
            listener.onMsg(msg.obj)
        } else {
            Log.d("CHEN", "JBus error : 没有找到目标类，类名：" + c?.name + "\n")
        }
    }

    /**
     * 伪装成注册，其实就是添加一个监听，在onCreate中注册
     *
     * @param c        注册的对象，强制性以类为单位注册，也就是让某个类实现监听接口
     * @param listener 监听器
     */
    fun registerEvent(c: Class<*>, listener: OnEventMessageListener) {
        listenerMap[c.name] = listener
    }

    /**
     * 注销监听，在onDestroy中注销
     *
     * @param
     */
    fun unregisterEvent(c: Class<*>) {
        listenerMap.remove(c.name)
    }

    /**
     * 群发消息
     *
     * @param o 发送的内容
     */
    fun <T> sendMsg(o: T) {
        handler.sendMessage(handler.obtainMessage(SEND_ALL, o))

    }

    /**
     * 发送指定对象
     *
     * @param c 需要发送的对象
     * @param o 发送的内容
     */
    fun <T> sendMsg(c: Class<*>, o: T) {
        this.c = c
        handler.sendMessage(handler.obtainMessage(SEND_SINGLE, o))
    }

    interface OnEventMessageListener {
        fun <T> onMsg(s: T)
    }
}