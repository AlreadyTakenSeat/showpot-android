package com.alreadyoccupiedseat.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ShowPotFcmService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    }

    override fun onNewToken(token: String) {

    }

    companion object {
        private const val FCM_CHANNEL = 123456789
        private const val FCM_SERVICE_CHANNEL = "FCM_SERVICE_CHANNEL"
        private const val TAG = "ShowPotFcmService"
    }
}
