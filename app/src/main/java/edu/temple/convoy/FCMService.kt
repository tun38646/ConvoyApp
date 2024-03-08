package edu.temple.convoy

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class FCMService : FirebaseMessagingService() {

    companion object {
        val UPDATE_ACTION = "convoy_action_update"
        val UPDATE_KEY = "convoy_update_key"
    }

    override fun onNewToken(token: String) {
        Helper.user.clearToken(this)
        Helper.user.registerTokenFlow(this, token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM Message", message.data["payload"].toString())

        val msg = JSONObject(message.data["payload"].toString())

        when (msg.getString("action")) {
            "UPDATE" -> {
                sendBroadcast(Intent(UPDATE_ACTION).putExtra(UPDATE_KEY, msg.getJSONArray("data").toString()))
            }
        }
    }
}