package com.alreadyoccupiedseat.notification

import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ShowPotFcmService @Inject constructor() : FirebaseMessagingService() {

    @Inject
    lateinit var accountDataStore: AccountDataStore

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

    }

    override fun onNewToken(token: String) {
        CoroutineScope(Dispatchers.Default).launch {
            accountDataStore.updateFcmToken(token)
        }
    }

    companion object {
        private const val FCM_CHANNEL = 123456789
        private const val FCM_SERVICE_CHANNEL = "FCM_SERVICE_CHANNEL"
        private const val TAG = "ShowPotFcmService"
    }
}
