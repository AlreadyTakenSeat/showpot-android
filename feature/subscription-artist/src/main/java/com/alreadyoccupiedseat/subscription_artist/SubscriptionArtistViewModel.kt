package com.alreadyoccupiedseat.subscription_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubscriptionArtistScreenEvent {
    data object Idle : SubscriptionArtistScreenEvent

    data object SubscribeArtistsSuccess : SubscriptionArtistScreenEvent
}

data class SubscriptionArtistScreenState(
    val selectedArtists: List<Artist> = emptyList(),
    val unsubscribedArtists: List<Artist> = emptyList(),
    val isLoggedIn: Boolean = false,
    val isSheetVisible: Boolean = false,
)


@HiltViewModel
class SubscriptionArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(SubscriptionArtistScreenState())
    val state = _state

    private val _event = MutableSharedFlow<SubscriptionArtistScreenEvent>()
    val event = _event

    init {
        getUnsubscribedArtists()
        viewModelScope.launch {
            accountDataStore.getAccessTokenFlow().collect {
                _state.value = _state.value.copy(
                    isLoggedIn = it?.isNotEmpty() ?: false,
                )
            }
        }
    }

    fun subscribeArtists() {
        viewModelScope.launch {
            val artistIds = state.value.selectedArtists.map { it.id }
            val subscribedArtistsIds = artistRepository.subscribeArtists(artistIds).map {
                it.id
            }

            _state.value = _state.value.copy(
                selectedArtists = emptyList(),
                unsubscribedArtists = state.value.unsubscribedArtists.filter {
                    it.id !in subscribedArtistsIds
                },
            )
            event.emit(SubscriptionArtistScreenEvent.SubscribeArtistsSuccess)
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

    fun setSheetVisible(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isSheetVisible = isVisible,
        )
    }

    private fun getUnsubscribedArtists() {
        viewModelScope.launch {
            val result = artistRepository.getUnsubscribedArtists(
                size = 30,
            )

            _state.value = _state.value.copy(
                unsubscribedArtists = result,
            )
        }
    }
}