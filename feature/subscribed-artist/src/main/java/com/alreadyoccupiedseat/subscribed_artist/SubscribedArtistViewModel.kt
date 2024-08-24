package com.alreadyoccupiedseat.subscribed_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
class SubscribedArtistViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SubscribedArtistState>(SubscribedArtistState())
    val state = _state

    private val _event = MutableStateFlow<SubscribedArtistEvent>(SubscribedArtistEvent.Idle)
    val event = _event

    init {
        loadSubscribedArtist()
    }

    private fun loadSubscribedArtist() {
        viewModelScope.launch {
            // TODO RealData
            delay(3000)
            _state.value = _state.value.copy(subscribedArtists =
            (1..10).map { index ->
                val posterImageURL = if (index % 2 != 0) {
                    "https://www.dailypop.kr/news/photo/201509/12537_8555_1158.JPG"
                } else {
                    "https://cdnimage.dailian.co.kr/news/201502/news_1423016119_486190_m_1.jpg"
                }
                Artist(
                    id = index.toString(),
                    imageURL = posterImageURL,
                    koreanName = "Artist $index",
                    englishName = "Artist $index"
                )
            })
        }
    }

    fun deleteSubscribedArtist(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                subscribedArtists = _state.value.subscribedArtists.filter { it.id != id }
            )
        }
    }

}