package com.alreadyoccupiedseat.subscription_genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubscriptionGenreScreenEvent {
    data object Idle : SubscriptionGenreScreenEvent
}

data class SubscriptionGenreScreenState(
    val selectedGenre: List<Genre> = emptyList(),
)

@HiltViewModel
class SubscriptionGenreViewModel @Inject constructor(

) : ViewModel() {

    val state = MutableStateFlow(SubscriptionGenreScreenState())
    val event = MutableStateFlow(SubscriptionGenreScreenEvent.Idle)

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
    )

    fun checkAllGenresSubscribed() {
        // TODO EnumClass, 조회
//        _isAllGenresSubscribed.value = genreList.all { it.isSubscribed }
    }

    fun subscribeGenres() {
        viewModelScope.launch {

        }
    }

}