package com.alreadyoccupiedseat.subscription_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.Show
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubscriptionArtistScreenEvent {
    data object Idle : SubscriptionArtistScreenEvent
}

data class SubscriptionArtistScreenState(
    val selectedArtists: List<Artist> = emptyList(),
)


class SubscriptionArtistViewModel @Inject constructor(): ViewModel() {

    val state = MutableStateFlow(SubscriptionArtistScreenState())
    val event = MutableStateFlow(SubscriptionArtistScreenEvent.Idle)

    fun subscribeArtists() {
        viewModelScope.launch {

        }
    }
}