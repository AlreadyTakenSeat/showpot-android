package com.alreadyoccupiedseat.subscription_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.common.utiils.errorLog
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.artist.UnSubscribedArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface SubscriptionArtistScreenEvent {
    data object Idle : SubscriptionArtistScreenEvent

    data object SubscribeArtistsSuccess : SubscriptionArtistScreenEvent
}

data class SubscriptionArtistScreenState(
    val selectedArtists: List<UnSubscribedArtist> = emptyList(),
    val unsubscribedArtists: List<UnSubscribedArtist> = emptyList(),
    val isLoggedIn: Boolean = false,
    val isSheetVisible: Boolean = false,
)


@HiltViewModel
class SubscriptionArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val accountDataStore: AccountDataStore
) : ViewModel(), ContainerHost<SubscriptionArtistScreenState, SubscriptionArtistScreenEvent> {

    override val container: Container<SubscriptionArtistScreenState, SubscriptionArtistScreenEvent> =
        container(SubscriptionArtistScreenState())

    init {
        getUnsubscribedArtists()
        intent {
            accountDataStore.getAccessTokenFlow().collect {
                reduce {
                    state.copy(
                        isLoggedIn = it?.isNotEmpty() ?: false,
                    )
                }
            }
        }
    }

    fun subscribeArtists() = intent {

        val artistIds = state.selectedArtists.map { it.spotifyId }
        val result = artistRepository.subscribeArtists(artistIds)

        result.onSuccess { subscribedArtistsInfo ->
            val subscribedIds = subscribedArtistsInfo.map {
                it.id
            }

            reduce {
                state.copy(
                    selectedArtists = emptyList(),
                    unsubscribedArtists = state.unsubscribedArtists.filter {
                        it.id !in subscribedIds
                    },
                )
            }

            postSideEffect(SubscriptionArtistScreenEvent.SubscribeArtistsSuccess)
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

    }

    fun selectArtist(artist: UnSubscribedArtist) = intent {

        if (state.selectedArtists.contains(artist)) {
            reduce {
                state.copy(
                    selectedArtists = state.selectedArtists - artist,
                )
            }
        } else {
            reduce {
                state.copy(
                    selectedArtists = state.selectedArtists + artist,
                )
            }
        }

    }

    fun isSelected(artist: UnSubscribedArtist): Boolean {
        return container.stateFlow.value.selectedArtists.contains(artist)
    }

    fun setSheetVisible(isVisible: Boolean) = intent {
        reduce {
            state.copy(
                isSheetVisible = isVisible,
            )
        }
    }

    private fun getUnsubscribedArtists() = intent {
        val result = artistRepository.getUnsubscribedArtists(
            size = 30,
        )

        reduce {
            state.copy(
                unsubscribedArtists = result,
            )
        }
    }
}