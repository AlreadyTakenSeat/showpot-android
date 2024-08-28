package com.alreadyoccupiedseat.enum

import androidx.annotation.DrawableRes
import com.alreadyoccupiedseat.designsystem.R

/** Genre Drawable Res ***/
enum class GenreType(
    val id: String,
    val genreName: String,
    @DrawableRes val normalRes: Int,
    @DrawableRes val selectedRes: Int,
    @DrawableRes val subscribedRes: Int
) {
    ROCK(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d1",
        genreName = "rock",
        normalRes = R.drawable.img_genre_rock,
        selectedRes = R.drawable.img_genre_selected_rock,
        subscribedRes = R.drawable.img_genre_subscribed_rock
    ),
    HIPHOP(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d2",
        genreName = "hiphop",
        normalRes = R.drawable.img_genre_hiphop,
        selectedRes = R.drawable.img_genre_selected_hiphop,
        subscribedRes = R.drawable.img_genre_subscribed_hiphop
    ),
    BAND(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3",
        genreName = "band",
        normalRes = R.drawable.img_genre_band,
        selectedRes = R.drawable.img_genre_selected_band,
        subscribedRes = R.drawable.img_genre_subscribed_band
    ),
    JPOP(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d4",
        genreName = "jpop",
        normalRes = R.drawable.img_genre_jpop,
        selectedRes = R.drawable.img_genre_selected_jpop,
        subscribedRes = R.drawable.img_genre_subscribed_jpop
    ),
    POP(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d5",
        genreName = "pop",
        normalRes = R.drawable.img_genre_pop,
        selectedRes = R.drawable.img_genre_selected_pop,
        subscribedRes = R.drawable.img_genre_subscribed_pop
    ),
    HOUSE(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d6",
        genreName = "house",
        normalRes = R.drawable.img_genre_house,
        selectedRes = R.drawable.img_genre_selected_house,
        subscribedRes = R.drawable.img_genre_subscribed_house
    ),
    EDM(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d7",
        genreName = "edm",
        normalRes = R.drawable.img_genre_edm,
        selectedRes = R.drawable.img_genre_selected_edm,
        subscribedRes = R.drawable.img_genre_subscribed_edm
    ),
    MUSICAL(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d8",
        genreName = "musical",
        normalRes = R.drawable.img_genre_musical,
        selectedRes = R.drawable.img_genre_selected_musical,
        subscribedRes = R.drawable.img_genre_subscribed_musical
    ),
    RNB(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876d9",
        genreName = "rnb",
        normalRes = R.drawable.img_genre_rnb,
        selectedRes = R.drawable.img_genre_selected_rnb,
        subscribedRes = R.drawable.img_genre_subscribed_rnb
    ),
    OPERA(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876da",
        genreName = "opera",
        normalRes = R.drawable.img_genre_opera,
        selectedRes = R.drawable.img_genre_selected_opera,
        subscribedRes = R.drawable.img_genre_subscribed_opera
    ),
    METAL(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876db",
        genreName = "metal",
        normalRes = R.drawable.img_genre_metal,
        selectedRes = R.drawable.img_genre_selected_metal,
        subscribedRes = R.drawable.img_genre_subscribed_metal
    ),
    CLASSIC(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876dc",
        genreName = "classic",
        normalRes = R.drawable.img_genre_classic,
        selectedRes = R.drawable.img_genre_selected_classic,
        subscribedRes = R.drawable.img_genre_subscribed_classic
    ),
    JAZZ(
        id = "017f20d0-4f3c-8f4d-9e15-7ff0c3a876dd",
        genreName = "jazz",
        normalRes = R.drawable.img_genre_jazz,
        selectedRes = R.drawable.img_genre_selected_jazz,
        subscribedRes = R.drawable.img_genre_subscribed_jazz
    );

    companion object {
        /** 기본 리소스 전체 반환 ***/
        fun getAllGenreNormalRes(): List<Int> {
            return entries.map { it.normalRes }
        }
        /** 기본 리소스 반환 ***/
        fun getNormalRes(id: String): Int {
            return entries.firstOrNull { it.id == id }?.normalRes
                ?: throw IllegalArgumentException("Invalid Genre ID: $id")
        }

        /** 선택된 리소스 반환 ***/
        fun getSelectedRes(id: String): Int {
            return entries.firstOrNull { it.id == id }?.selectedRes
                ?: throw IllegalArgumentException("Invalid Genre ID: $id")
        }

        /** 구독된 리소스 반환 ***/
        fun getSubscribedRes(id: String): Int {
            return entries.firstOrNull { it.id == id }?.subscribedRes
                ?: throw IllegalArgumentException("Invalid Genre ID: $id")
        }
    }

}
