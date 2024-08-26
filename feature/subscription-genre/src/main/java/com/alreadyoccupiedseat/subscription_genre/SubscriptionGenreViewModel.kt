package com.alreadyoccupiedseat.subscription_genre

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.genre.GenreRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubscriptionGenreScreenEvent {
    data object Idle : SubscriptionGenreScreenEvent
}

data class SubscriptionGenreScreenState(
    val genres: List<Genre> = emptyList(),
    val selectedGenre: List<Genre> = emptyList(),
    val isLoggedIn: Boolean = false,
    val tempGenreList: List<Pair<Int, Int>> = emptyList(),
)

@HiltViewModel
class SubscriptionGenreViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore,
    private val genreRepository: GenreRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SubscriptionGenreScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableStateFlow(SubscriptionGenreScreenEvent.Idle)
    val event = _event.asSharedFlow()

    init {
        getAllGenres()
        viewModelScope.launch {
            accountDataStore.getAccessToken()?.let {
                _state.value = _state.value.copy(isLoggedIn = true)
            }
        }
    }

    // 임시
    val tempGenreList = listOf(
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_rock to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_rock,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_band to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_band,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_edm to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_edm,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_classic to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_classic,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_hiphop to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_hiphop,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_house to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_house,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_opera to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_opera,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_pop to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_pop,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_rnb to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_rnb,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_musical to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_musical,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_metal to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_metal,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_jpop to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_jpop,
        com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_jazz to com.alreadyoccupiedseat.designsystem.R.drawable.img_genre_selected_jazz,
    )

    fun subscribeGenre() {
        viewModelScope.launch {
//            val genreId = state.value.selectedGenre.map { it.id }
//            val subscribedArtistsIds = genreRepository.subscribeArtists(artistIds)
//            _state.value = _state.value.copy(
//                selectedGenre = emptyList(),
//                unsubscribedGenres = state.value.unsubscribedGenres.filter { it.id !in subscribeArtists() },
//            )
        }
    }

    fun selectGenre(genre: Genre) {
        if (state.value.selectedGenre.contains(genre)) {
            _state.value = _state.value.copy(
                selectedGenre = _state.value.selectedGenre - genre,
            )
        } else {
            _state.value = _state.value.copy(
                selectedGenre = _state.value.selectedGenre + genre,
            )
        }

    }

    fun isSelected(genre: Genre): Boolean {
        return state.value.selectedGenre.contains(genre)
    }

    private fun getAllGenres() {
        viewModelScope.launch {
            val result = genreRepository.getGenres(
                size = 20,
            )
            Log.w("getAllGenres", "getAllGenres: $result")
            _state.value = _state.value.copy(
                genres = result,
            )
        }
    }

}