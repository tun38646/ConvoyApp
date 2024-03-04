package edu.temple.convoy

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MessagingService"
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")
            try {
                val jsonPayload = JSONObject(message.data["payload"])

                // Extract action from payload
                val action = jsonPayload.getString("action")

                // Handle different types of actions
                when (action) {
                    "UPDATE" -> handleUpdateMessage(jsonPayload.getJSONArray("data"))
                    "END" -> handleEndMessage(jsonPayload.getString("convoy_id"))
                    else -> Log.d(TAG, "Unhandled action: $action")
                }
            } catch (e: JSONException) {
                Log.e(TAG, "Error parsing JSON payload: ${e.message}")
            }
        }
    }

    private fun handleUpdateMessage(dataArray: JSONArray) {
        for (i in 0 until dataArray.length()) {
            val userData = dataArray.getJSONObject(i)
            val username = userData.getString("username")
            val firstName = userData.getString("firstname")
            val lastName = userData.getString("lastname")
            val latitude = userData.getDouble("latitude")
            val longitude = userData.getDouble("longitude")

            // Process user data here
            Log.d(TAG, "Received UPDATE message for user: $username")
            Log.d(TAG, "First Name: $firstName")
            Log.d(TAG, "Last Name: $lastName")
            Log.d(TAG, "Latitude: $latitude")
            Log.d(TAG, "Longitude: $longitude")
        }
    }

    private fun handleEndMessage(convoyId: String) {
        // Process convoy end message here
        Log.d(TAG, "Received END message for convoy: $convoyId")
    }
}