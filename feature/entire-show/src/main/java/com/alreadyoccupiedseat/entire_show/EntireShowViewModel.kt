package com.alreadyoccupiedseat.entire_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.Data
import com.alreadyoccupiedseat.model.Genre
import com.alreadyoccupiedseat.model.ShowTicketingTime
import com.alreadyoccupiedseat.model.Shows
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface EntireShowEvent {
    data object Error: EntireShowEvent
}

data class EntireShowState(
    val entireShowList: Shows = Shows(data = emptyList(), hasNext = false, size = 0),
)

@HiltViewModel
class EntireShowViewModel @Inject constructor(): ViewModel() {


    private val _state = MutableStateFlow<EntireShowState>(EntireShowState())
    val state = _state

    init {
        loadShowsWithDelay()
    }

    private fun loadShowsWithDelay() {
        viewModelScope.launch {
            // TODO RealData
            val shows = (1..10).map { index ->
                val genres = listOf(
                    Genre(
                        id = index.toString(),
                        name = when (index % 13) {
                            0 -> "Rock"
                            1 -> "Band"
                            2 -> "EDM"
                            3 -> "Classic"
                            4 -> "Hiphop"
                            5 -> "House"
                            6 -> "Opera"
                            7 -> "Pop"
                            8 -> "Rnb"
                            9 -> "Musical"
                            10 -> "Metal"
                            11 -> "Jpop"
                            else -> "Jazz"
                        }
                    )
                )
                val posterImageURL = if (index % 2 != 0) {
                    "https://cdn.newslock.co.kr/news/photo/201903/16338_12349_86.jpg"
                } else {
                    "https://media.bunjang.co.kr/product/264256809_2_1721221361_w360.jpg"
                }

                Data(
                    artists = listOf(
                        Artist(
                            id = index.toString(),
                            imageURL = "https://example.com/artist$index.jpg",
                            koreanName = "Artist $index",
                            englishName = "Artist $index"
                        )
                    ),
                    genres = genres,
                    hasTicketingOpenSchedule = index % 2 != 0,
                    id = index.toString(),
                    location = "Location $index",
                    posterImageURL = posterImageURL,
                    reservationAt = "2024-08-${index + 10}T10:00:00Z",
                    showTicketingTimes = listOf(
                        ShowTicketingTime(
                            ticketingAt = "2024-08-${index + 15}T10:00:00Z",
                            ticketingType = if (index % 2 == 0) "Presale" else "General"
                        )
                    ),
                    title = "Concert $index",
                    viewCount = index * 100
                )
            }

            _state.value = _state.value.copy(
                entireShowList = Shows(
                    data = shows,
                    hasNext = true,
                    size = shows.size
                )
            )
        }
    }


}