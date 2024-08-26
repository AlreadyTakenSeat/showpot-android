package com.alreadyoccupiedseat.enum

import androidx.annotation.DrawableRes
import com.alreadyoccupiedseat.designsystem.R

/** Genre Drawable Res ***/
enum class Genre(
    @DrawableRes val normalRes: Int,
    @DrawableRes val selectedRes: Int,
    @DrawableRes val subscribedRes: Int
) {
    ROCK(
        normalRes = R.drawable.img_genre_rock,
        selectedRes = R.drawable.img_genre_selected_rock,
        subscribedRes = R.drawable.img_genre_subscribed_rock
    ),
    BAND(
        normalRes = R.drawable.img_genre_band,
        selectedRes = R.drawable.img_genre_selected_band,
        subscribedRes = R.drawable.img_genre_subscribed_band
    ),
    EDM(
        normalRes = R.drawable.img_genre_edm,
        selectedRes = R.drawable.img_genre_selected_edm,
        subscribedRes = R.drawable.img_genre_subscribed_edm
    ),
    CLASSIC(
        normalRes = R.drawable.img_genre_classic,
        selectedRes = R.drawable.img_genre_selected_classic,
        subscribedRes = R.drawable.img_genre_subscribed_classic
    ),
    HIPHOP(
        normalRes = R.drawable.img_genre_hiphop,
        selectedRes = R.drawable.img_genre_selected_hiphop,
        subscribedRes = R.drawable.img_genre_subscribed_hiphop
    ),
    HOUSE(
        normalRes = R.drawable.img_genre_house,
        selectedRes = R.drawable.img_genre_selected_house,
        subscribedRes = R.drawable.img_genre_subscribed_house
    ),
    OPERA(
        normalRes = R.drawable.img_genre_opera,
        selectedRes = R.drawable.img_genre_selected_opera,
        subscribedRes = R.drawable.img_genre_subscribed_opera
    ),
    POP(
        normalRes = R.drawable.img_genre_pop,
        selectedRes = R.drawable.img_genre_selected_pop,
        subscribedRes = R.drawable.img_genre_subscribed_pop
    ),
    RNB(
        normalRes = R.drawable.img_genre_rnb,
        selectedRes = R.drawable.img_genre_selected_rnb,
        subscribedRes = R.drawable.img_genre_subscribed_rnb
    ),
    MUSICAL(
        normalRes = R.drawable.img_genre_musical,
        selectedRes = R.drawable.img_genre_selected_musical,
        subscribedRes = R.drawable.img_genre_subscribed_musical
    ),
    METAL(
        normalRes = R.drawable.img_genre_metal,
        selectedRes = R.drawable.img_genre_selected_metal,
        subscribedRes = R.drawable.img_genre_subscribed_metal
    ),
    JPOP(
        normalRes = R.drawable.img_genre_jpop,
        selectedRes = R.drawable.img_genre_selected_jpop,
        subscribedRes = R.drawable.img_genre_subscribed_jpop
    ),
    JAZZ(
        normalRes = R.drawable.img_genre_jazz,
        selectedRes = R.drawable.img_genre_selected_jazz,
        subscribedRes = R.drawable.img_genre_subscribed_jazz
    );
    companion object {
        /**  기본 ***/
        fun getNormalRes(genre: Genre): Int {
            return genre.normalRes
        }
        /** 선택 ***/
        fun getSelectedRes(genre: Genre): Int {
            return genre.selectedRes
        }
        /** 구독 ***/
        fun getSubscribedRes(genre: Genre): Int {
            return genre.subscribedRes
        }
    }
}
