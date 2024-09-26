package com.alreadyoccupiedseat.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.datastore.SearchHistoryDataStore
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.SubscribedArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchScreenEvent {
    data object Idle : SearchScreenEvent

    data object SubscribeArtistSuccess : SearchScreenEvent

    data object UnSubscribeArtistSuccess : SearchScreenEvent
}

data class SearchScreenState(
    val searchHistory: List<String> = emptyList(),
    val inputText: String = String.EMPTY,
    val isSearchedScreen: Boolean = false,
    val searchedArtists: List<SubscribedArtist> = emptyList(),
    val searchedShows: List<SearchedShow> = emptyList(),
    val isArtistUnSubscriptionSheetVisible: Boolean = false,
    val isLoginSheetVisible: Boolean = false,
    val unSubscribeTargetArtist: SubscribedArtist? = null,
    val isLoggedIn: Boolean = false
)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryDataStore: SearchHistoryDataStore,
    private val artistRepository: ArtistRepository,
    private val showRepository: ShowRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel() {

    private var _state = MutableStateFlow<SearchScreenState>(SearchScreenState())
    val state = _state

    private var _event = MutableSharedFlow<SearchScreenEvent>()
    val event = _event

    init {
        viewModelScope.launch {
            val searchHistory = searchHistoryDataStore.getSearchedKeyword()
            _state.value = _state.value.copy(searchHistory = searchHistory.reversed())
        }

        viewModelScope.launch {
            accountDataStore.getAccessTokenFlow().collect {
                _state.value = _state.value.copy(isLoggedIn = it?.isNotEmpty() ?: false)
            }
        }
    }

    fun updateInputText(inputText: String) {
        _state.value = _state.value.copy(inputText = inputText)
    }

    private fun stateChangeToSearched() {
        _state.value = _state.value.copy(
            inputText = _state.value.inputText,
            isSearchedScreen = true
        )
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
            val updatedSearchHistory =
                searchHistoryDataStore.deleteSearchedKeyword(targetKeyword)
            _state.value = _state.value.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            val updatedSearchHistory = searchHistoryDataStore.initSearchedKeyword()
            _state.value = _state.value.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun changeArtistUnSubscriptionSheetVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isArtistUnSubscriptionSheetVisible = isVisible
        )
    }

    fun changeLoginSheetVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isLoginSheetVisible = isVisible
        )
    }

    fun changeUnSubscribeTargetArtist(subscribedArtist: SubscribedArtist) {
        _state.value = _state.value.copy(
            unSubscribeTargetArtist = subscribedArtist
        )
    }

    fun searchArtistsAndShows() {

        viewModelScope.launch {
            searchArtists()
            searchShows()
            stateChangeToSearched()
        }
    }

    fun subscribeArtist(artistId: String) {
        viewModelScope.launch {
            val result = artistRepository.subscribeArtists(listOf(artistId))

            _state.value = _state.value.copy(
                searchedArtists = _state.value.searchedArtists.map {
                    if (it.id == result.first()) {
                        it.copy(isSubscribed = true)
                    } else {
                        it
                    }
                }
            )

            _event.emit(SearchScreenEvent.SubscribeArtistSuccess)
        }
    }

    fun unSubscribeArtist() {
        viewModelScope.launch {
            state.value.unSubscribeTargetArtist?.let { targetArtist ->
                val result = artistRepository.unSubscribeArtists(listOf(targetArtist.id))

                _state.value = _state.value.copy(
                    searchedArtists = _state.value.searchedArtists.map {
                        if (it.id == result.first()) {
                            it.copy(isSubscribed = false)
                        } else {
                            it
                        }
                    }
                )

                _event.emit(SearchScreenEvent.UnSubscribeArtistSuccess)
            }
        }
    }

    private fun searchArtists() {
        viewModelScope.launch {
            val searchedArtists = artistRepository.searchArtists(
                size = 30,
                search = _state.value.inputText,
            )

            _state.value = _state.value.copy(searchedArtists = searchedArtists)
        }
    }

    private fun searchShows() {
        viewModelScope.launch {
            val searchedShows = showRepository.searchShows(
                size = 30,
                search = _state.value.inputText,
            )

            _state.value = _state.value.copy(searchedShows = searchedShows)
        }
    }
}