package com.alreadyoccupiedseat.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.datastore.SearchHistoryDataStore
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.Genre
import com.alreadyoccupiedseat.model.Show
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchScreenEvent {
    data object Idle : SearchScreenEvent
}

data class SearchScreenState(
    val searchHistory: List<String> = emptyList(),
    val inputText: String = String.EMPTY,
    val isSearchedScreen: Boolean = false,
    val searchedArtists: List<Artist> = emptyList(),
    val searchedShows: List<Show> = emptyList(),
)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryDataStore: SearchHistoryDataStore
) : ViewModel() {

    private var _state = MutableStateFlow<SearchScreenState>(SearchScreenState())
    val state = _state

    private var _event = MutableStateFlow<SearchScreenEvent>(SearchScreenEvent.Idle)
    val event = _event

    init {
        viewModelScope.launch {
            val searchHistory = searchHistoryDataStore.getSearchedKeyword()
            _state.value = _state.value.copy(searchHistory = searchHistory.reversed())
        }
        searchArtistsAndShows()
    }

    fun updateInputText(inputText: String) {
        _state.value = _state.value.copy(inputText = inputText)
    }

    fun stateChangeToSearched() {
        _state.value = _state.value.copy(isSearchedScreen = true)
    }

    fun stateChangeToNotSearched() {
        _state.value = _state.value.copy(isSearchedScreen = false)
    }

    fun updateSearchHistories() {
        viewModelScope.launch {
            val newKeyword = _state.value.inputText
            val updatedSearchHistory = searchHistoryDataStore.updateSearchedKeyword(newKeyword)
            _state.value = _state.value.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun deleteSearchHistory(targetKeyword: String) {
        viewModelScope.launch {
            val updatedSearchHistory = searchHistoryDataStore.deleteSearchedKeyword(targetKeyword)
            _state.value = _state.value.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            val updatedSearchHistory = searchHistoryDataStore.initSearchedKeyword()
            _state.value = _state.value.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun searchArtistsAndShows() {
        viewModelScope.launch {
            // TODO: Changed to real data
            val searchedArtists = listOf(
                Artist(
                    id = "1",
                    name = "Dua Lipa",
                    image = ""
                )
            )

            _state.value = _state.value.copy(searchedArtists = searchedArtists)

            val searchedShows = listOf(
                Show(
                    artist = Artist(
                        id = "1",
                        name = "Dua Lipa",
                        image = ""
                    ),
                    genre = Genre(
                        id = "1",
                        name = "Pop",
                        image = ""
                    ),
                    id = "1",
                    name = "Dua Lipa Studio Live",
                    posterImageURL = "",
                    ticketingAndShowInfo = emptyList()
                )
            )
            _state.value = _state.value.copy(searchedShows = searchedShows)
        }
    }
}