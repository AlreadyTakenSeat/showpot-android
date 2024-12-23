package com.alreadyoccupiedseat.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.artist.ArtistRepository
import com.alreadyoccupiedseat.data.login.LoginRepository
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.show.ShowType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    val genreList: List<Pair<Int, Int>> = emptyList(),
    val entireShowList: List<ShowPreview> = emptyList(),
    val recommendedShowList: List<ShowPreview> = emptyList(),
    val unSubscribedArtists: List<Artist> = emptyList(),
    val nickName: String = String.EMPTY
)

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val showRepository: ShowRepository,
    private val artistRepository: ArtistRepository,
    private val loginRepository: LoginRepository,
) : ViewModel(){

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state

    private val genreList = listOf(
        R.drawable.img_genre_rock to R.drawable.img_genre_selected_rock,
        R.drawable.img_genre_band to R.drawable.img_genre_selected_band,
        R.drawable.img_genre_edm to R.drawable.img_genre_selected_edm,
        R.drawable.img_genre_classic to R.drawable.img_genre_selected_classic,
        R.drawable.img_genre_hiphop to R.drawable.img_genre_selected_hiphop,
        R.drawable.img_genre_house to R.drawable.img_genre_selected_house,
        R.drawable.img_genre_opera to R.drawable.img_genre_selected_opera,
        R.drawable.img_genre_pop to R.drawable.img_genre_selected_pop,
        R.drawable.img_genre_rnb to R.drawable.img_genre_selected_rnb,
        R.drawable.img_genre_musical to R.drawable.img_genre_selected_musical,
        R.drawable.img_genre_metal to R.drawable.img_genre_selected_metal,
        R.drawable.img_genre_jpop to R.drawable.img_genre_selected_jpop,
    )

    init {
        getEntireShow()
        getRecommendedShow()
        _state.value = _state.value.copy(
            genreList = genreList
        )
    }

    /** 전체 공연 목록 가져오기 ***/
    // 이름 변경
    private fun getEntireShow() {
        viewModelScope.launch {
            val tempRequestSize = 30
            val shows = showRepository.getEntireShow(
                sort = ShowType.RECENT.name,
                onlyOpenSchedule = false,
                size = tempRequestSize,
            )

            _state.value = _state.value.copy(
                entireShowList = shows.take(2),
            )
        }
    }

    private fun getRecommendedShow() {
        viewModelScope.launch {
            val tempRequestSize = 30
            val shows = showRepository.getEntireShow(
                sort = ShowType.POPULAR.name,
                onlyOpenSchedule = false,
                size = tempRequestSize,
            )

            _state.value = _state.value.copy(
                recommendedShowList = shows
            )
        }
    }

    fun getUbSubscribedArtists() {
        viewModelScope.launch {
            val unSubscribedArtists = artistRepository.getUnsubscribedArtists(
                size = 10
            )

            _state.value = _state.value.copy(
                unSubscribedArtists = unSubscribedArtists
            )
        }
    }

    fun getNickName() {
        viewModelScope.launch {
            loginRepository.getProfile().onSuccess { profile ->
                _state.value = state.value.copy(nickName = profile.nickname)
            }
        }
    }

}