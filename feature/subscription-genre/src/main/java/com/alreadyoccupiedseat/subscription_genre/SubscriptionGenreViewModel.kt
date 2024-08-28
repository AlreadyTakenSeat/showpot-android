package com.alreadyoccupiedseat.subscription_genre

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.genre.GenreRepository
import com.alreadyoccupiedseat.datastore.AccountDataStore
import com.alreadyoccupiedseat.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubscriptionGenreScreenEvent {
    data object Idle : SubscriptionGenreScreenEvent
}

data class SubscriptionGenreScreenState(
    val genres: List<Genre> = emptyList(),
    val selectedGenre: List<Genre> = emptyList(),
    val subscribedGenre: List<Genre> = emptyList(),
    val unSelectedGenre: Genre? = null,
    val isLoggedIn: Boolean = false,
    val isLoginRequestBottomSheetVisible: Boolean = false,
    val isSubscriptionSheetVisible: Boolean = false,
    val isUnSubscriptionSheetVisible: Boolean = false,
)

@HiltViewModel
class SubscriptionGenreViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore,
    private val genreRepository: GenreRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SubscriptionGenreScreenState())
    val state = _state.asStateFlow()

    init {
        getAllGenres()
        viewModelScope.launch {
            accountDataStore.getAccessTokenFlow().collectLatest { token ->
                _state.value = _state.value.copy(isLoggedIn = token != null)
            }
        }
    }

    /** 모든 장르 **/
    private fun getAllGenres() {
        viewModelScope.launch {
            val result = genreRepository.getGenres(
                size = 20,
            )
            Log.w("getAllGenres", "getAllGenres: $result")
            _state.value = _state.value.copy(
                genres = result,
                subscribedGenre = result.filter { it.isSubscribed }
            )
        }
    }

    /** 장르를 구독 **/
    fun subscribeGenre() {
        viewModelScope.launch {
            val genreIds = state.value.selectedGenre.map { it.id }
            Log.d("subscribeGenre", "subscribeGenre: $genreIds")
            genreRepository.subscribeGenres(genreIds).let {
                _state.value = _state.value.copy(
                    selectedGenre = emptyList(),
                    subscribedGenre = state.value.subscribedGenre + state.value.genres.filter { genre -> genreIds.contains(genre.id) }
                )
            }
        }
    }

    /** 장르 구독을 취소  **/
    fun unSubscribeGenre() {
        viewModelScope.launch {
            state.value.unSelectedGenre?.id?.let { unSelectedGenreId ->
                Log.d("unSubscribeGenre", "unSubscribeGenre: $unSelectedGenreId")
                genreRepository.unsubscribeGenres(listOf(unSelectedGenreId)).let {
                    _state.value = _state.value.copy(
                        subscribedGenre = state.value.subscribedGenre.filter { it.id != unSelectedGenreId },
                        unSelectedGenre = null,
                        isUnSubscriptionSheetVisible = false
                    )
                }
            }
        }
    }

    /** 장르 구독 해제를 위한 장르를 선택 **/
    fun selectUnSubscribeGenre(genre: Genre) {
        Log.d("selectUnSubscribeGenre", "selectUnSubscribeGenre: $genre")
        viewModelScope.launch {
            _state.value = _state.value.copy(
                unSelectedGenre = genre
            )
        }
    }

    /** 장르를 선택, 해제 **/
    fun selectGenre(genre: Genre) {
        Log.d("selectGenre", "selectGenre: $genre")
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

    /** 장르가 선택된 상태 확인 **/
    fun isSelected(genre: Genre): Boolean {
        return _state.value.selectedGenre.contains(genre)
    }

    /** 구독 해제 시트 Visible **/
    fun setUnSubscriptionSheetVisible(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isUnSubscriptionSheetVisible = isVisible,
        )
    }

    /** 로그인 요청 시트 Visible **/
    fun setLoginRequestSheetVisible(isVisible: Boolean) {
        _state.value = _state.value.copy(
            isLoginRequestBottomSheetVisible = isVisible,
        )
    }

}