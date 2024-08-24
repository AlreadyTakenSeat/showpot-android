package com.alreadyoccupiedseat.subscription_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubscriptionArtistScreenEvent {
    data object Idle : SubscriptionArtistScreenEvent
}

data class SubscriptionArtistScreenState(
    val selectedArtists: List<Artist> = emptyList(),
    val unsubscribedArtists: List<Artist> = emptyList(),
    val isLoggedIn: Boolean = false
)


@HiltViewModel
class SubscriptionArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel() {

    init {
        getUnsubscribedArtists()
        viewModelScope.launch {
            accountDataStore.getAccessToken()?.let {
                _state.value = _state.value.copy(isLoggedIn = true)
            }
        }
    }

    private var _state = MutableStateFlow(SubscriptionArtistScreenState())
    val state = _state

    val event = MutableStateFlow(SubscriptionArtistScreenEvent.Idle)

    fun subscribeArtists() {
        viewModelScope.launch {
            val artistIds = state.value.selectedArtists.map { it.id }
            val subscribedArtistsIds = artistRepository.subscribeArtists(artistIds)
            _state.value = _state.value.copy(
                selectedArtists = emptyList(),
                unsubscribedArtists = state.value.unsubscribedArtists.filter { it.id !in subscribedArtistsIds },
            )
        }
    }

    fun selectArtist(artist: Artist) {

        if (state.value.selectedArtists.contains(artist)) {
            _state.value = _state.value.copy(
                selectedArtists = _state.value.selectedArtists - artist,
            )
        } else {
            _state.value = _state.value.copy(
                selectedArtists = _state.value.selectedArtists + artist,
            )
        }

    }

    fun isSelected(artist: Artist): Boolean {
        return state.value.selectedArtists.contains(artist)
    }

    private fun getUnsubscribedArtists() {
        viewModelScope.launch {
            val result = artistRepository.getUnsubscribedArtists(
                size = 100,
            )

            _state.value = _state.value.copy(
                unsubscribedArtists = result,
            )
        }
    }
}