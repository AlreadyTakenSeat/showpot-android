package com.alreadyoccupiedseat.subscribed_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SubscribedArtistEvent {

    data object Idle : SubscribedArtistEvent
    data class DeleteSubscribedArtist(val id: String) : SubscribedArtistEvent
}

data class SubscribedArtistState(
    val subscribedArtists: List<Artist> = emptyList(),
)

@HiltViewModel
class SubscribedArtistViewModel @Inject constructor(
    private val aristRepository: ArtistRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SubscribedArtistState>(SubscribedArtistState())
    val state = _state

    private val _event = MutableStateFlow<SubscribedArtistEvent>(SubscribedArtistEvent.Idle)
    val event = _event

    fun getSubscribedArtist() {
        viewModelScope.launch {
            val result = aristRepository.getSubscribedArtists(
                size = 100
            )
            _state.value = _state.value.copy(subscribedArtists = result)
        }
    }

    fun deleteSubscribedArtist(id: String) {
        viewModelScope.launch {
            val unSubscribedArtists = aristRepository.unSubscribeArtists(
                artistIds = listOf(id)
            )

            _state.value =
                _state.value.copy(subscribedArtists = _state.value.subscribedArtists.filter { it.id !in unSubscribedArtists.first() })
        }
    }

}