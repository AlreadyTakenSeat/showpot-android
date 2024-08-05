package com.alreadyoccupiedseat.home

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.designsystem.R
import javax.inject.Inject

class HomeViewModel@Inject constructor() : ViewModel(){

    // 임시
    val genreList = listOf(
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

}