package com.lguplus.myalamrs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.data.alert.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface MyAlertsSettingEvent {
    data object Idle : MyAlertsSettingEvent
}

data class MyAlertsState(
    val test: Boolean = false,
)

@HiltViewModel
class MyAlertsViewModel @Inject constructor(
    private val alertRepository: AlertRepository
) : ViewModel(),
    ContainerHost<MyAlertsState, MyAlertsSettingEvent> {

    override val container: Container<MyAlertsState, MyAlertsSettingEvent> =
        container(MyAlertsState())

    init {
        intent {
            val isExist = alertRepository.getAlertsExist()
            val myAlerts = alertRepository.getAlerts(null, 30)
            Log.d("MyAlertsViewModel", "isExist: $isExist")
            Log.d("MyAlertsViewModel", "myAlerts: $myAlerts")
        }
    }

}