package com.alreadyoccupiedseat.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utiils.errorLog
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.datastore.SearchHistoryDataStore
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.SubscribedArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
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
) : ViewModel(), ContainerHost<SearchScreenState, SearchScreenEvent> {

    override val container: Container<SearchScreenState, SearchScreenEvent> =
        container(SearchScreenState())

    init {

        intent {
            val searchHistory = searchHistoryDataStore.getSearchedKeyword()
            reduce {
                state.copy(searchHistory = searchHistory.reversed())
            }
        }

        intent {
            accountDataStore.getAccessTokenFlow().collect {
                reduce {
                    state.copy(isLoggedIn = it?.isNotEmpty() ?: false)
                }
            }
        }
    }

    fun updateInputText(inputText: String) = intent {
        reduce {
            state.copy(inputText = inputText)
        }
    }

    private fun stateChangeToSearched() = intent {
        reduce {
            state.copy(
                inputText = state.inputText,
                isSearchedScreen = true
            )
        }
    }

    fun stateChangeToNotSearched() = intent {
        reduce {
            state.copy(
                isSearchedScreen = false,
                searchedArtists = emptyList(),
                searchedShows = emptyList()
            )
        }
    }

    fun updateSearchHistories() = intent {
        val newKeyword = state.inputText
        val updatedSearchHistory = searchHistoryDataStore.updateSearchedKeyword(newKeyword)
        reduce {
            state.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun deleteSearchHistory(targetKeyword: String) = intent {
        val updatedSearchHistory =
            searchHistoryDataStore.deleteSearchedKeyword(targetKeyword)
        reduce {
            state.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun deleteAllSearchHistory() = intent {
        val updatedSearchHistory = searchHistoryDataStore.initSearchedKeyword()
        reduce {
            state.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }

    fun changeArtistUnSubscriptionSheetVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(
                isArtistUnSubscriptionSheetVisible = isVisible
            )
        }
    }

    fun changeLoginSheetVisibility(isVisible: Boolean) = intent {
        reduce {
            state.copy(
                isLoginSheetVisible = isVisible
            )
        }
    }

    fun changeUnSubscribeTargetArtist(subscribedArtist: SubscribedArtist) = intent {
        reduce {
            state.copy(
                unSubscribeTargetArtist = subscribedArtist
            )
        }
    }

    fun searchArtistsAndShows() = intent {
        searchArtists()
        searchShows()
        stateChangeToSearched()
    }

    fun subscribeArtist(artistId: String) = intent {

        val result = artistRepository.subscribeArtists(listOf(artistId))

        reduce {
            state.copy(
                searchedArtists = state.searchedArtists.map {
                    if (it.id == result.first()) {
                        it.copy(isSubscribed = true)
                    } else {
                        it
                    }
                }
            )
        }

        postSideEffect(SearchScreenEvent.SubscribeArtistSuccess)
    }

    fun unSubscribeArtist() = intent {

        state.unSubscribeTargetArtist?.let { targetArtist ->

            val result = artistRepository.unSubscribeArtists(listOf(targetArtist.id))

            reduce {
                state.copy(
                    searchedArtists = state.searchedArtists.map {
                        if (it.id == result.first()) {
                            it.copy(isSubscribed = false)
                        } else {
                            it
                        }
                    }
                )
            }

            postSideEffect(SearchScreenEvent.UnSubscribeArtistSuccess)
        }

    }

    private fun searchArtists() = intent {

        val searchedArtists = artistRepository.searchArtists(
            size = 30,
            search = state.inputText,
        )

        searchedArtists.onSuccess { result ->
            reduce {
                state.copy(searchedArtists = result)
            }
        }.onFailure {
            errorLog(it.toApiErrorResult())
        }

    }

    private fun searchShows() = intent {

        val searchedShows = showRepository.searchShows(
            size = 30,
            search = state.inputText,
        )
        reduce {
            state.copy(searchedShows = searchedShows)
        }
    }
}