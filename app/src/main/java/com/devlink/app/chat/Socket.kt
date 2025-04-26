package com.devlink.app.chat

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object Socket {
    private const val SERVER_URL = "http://15.206.185.176:3000"

    private var mSocket: Socket? = null
    private var isInitialized = false

    private var messageCallback: ((String, String) -> Unit)? = null

    fun initSocket() {
        if (isInitialized) return

        try {
            val opts = IO.Options().apply {
                transports = arrayOf("websocket")
                reconnection = true
                forceNew = true
            }

            mSocket = IO.socket(SERVER_URL, opts)

            mSocket?.on(Socket.EVENT_CONNECT) {
                Log.i("Socket", "Connected to server ")
            }

            mSocket?.on(Socket.EVENT_DISCONNECT) {
                Log.i("Socket", "Disconnected from server ")
            }

            mSocket?.on(Socket.EVENT_CONNECT_ERROR) {
                Log.e("Socket", "Connection error: ${it.firstOrNull()}")
            }


            Log.d("Socket", "initSocket() called")

            mSocket?.on("messageReceived") { args ->
                try {
                    val data = args[0] as? JSONObject
                    val sender = data?.getString("firstName") ?: "Unknown"
                    val message = data?.getString("text") ?: ""
                    Log.i("Socket", "Message received from $sender: $message")
                    messageCallback?.invoke(sender, message)
                } catch (e: Exception) {
                    Log.e("Socket", "Error parsing incoming message: ${e.message}")
                }
            }

            mSocket?.connect()
            isInitialized = true

        } catch (e: Exception) {
            Log.e("Socket", "Socket init error: ${e.message}")
        }
    }


    fun emitJoinChat(firstName: String, userId: String, targetUserId: String) {
        val data = JSONObject().apply {
            put("firstName", firstName)
            put("userId", userId)
            put("targetUserId", targetUserId)
        }
        Log.i("Socket", "Joining chat room with data: $data")
        mSocket?.emit("joinChat", data)
    }

    fun emitSendMessage(
        firstName: String,
        lastName: String,
        userId: String,
        targetUserId: String,
        text: String
    ) {
        val data = JSONObject().apply {
            put("firstName", firstName)
            put("lastName", lastName)
            put("userId", userId)
            put("targetUserId", targetUserId)
            put("text", text)
        }
        Log.i("Socket", "Sending message: $data")
        mSocket?.emit("sendMessage", data)
    }


    fun onMessageReceived(callback: (String, String) -> Unit) {
        messageCallback = callback
    }

    fun disconnect() {
        mSocket?.disconnect()
        mSocket?.off() // remove all listeners
        isInitialized = false
        Log.i("Socket", "Socket disconnected manually")
    }
    fun isSocketInitialized(): Boolean {
        return mSocket != null && mSocket!!.connected()
    }
    fun debugSocketStatus() {
        Log.d("SocketDebug", "mSocket is ${if (mSocket == null) "NULL" else "NOT NULL"}")
        Log.d("SocketDebug", "isInitialized = $isInitialized")
        Log.d("SocketDebug", "Socket connected = ${mSocket?.connected()}")
    }

}