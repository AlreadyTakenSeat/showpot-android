package com.alreadyoccupiedseat.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistSubscription
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import com.alreadyoccupiedseat.model.Artist
import com.alreadyoccupiedseat.model.Show

// TODO: ShowInfo
@Composable
fun SearchedSection(
    searchedArtists: List<Artist>,
    searchedShows: List<Show>,
) {
    Column {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            ShowPotKoreanText_H2(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.artist), color = ShowpotColor.Gray100
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(modifier = Modifier.width(4.dp)) }
            searchedArtists.forEach {artist ->
                item {
                    ShowPotArtistSubscription(
                        // TODO: Change to real artist image
                        icon = painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.img_artist_default),
                        text = artist.englishName
                    )
                }
            }
        }

        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
                .padding(top = 36.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            ShowPotKoreanText_H2(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.show_information), color = ShowpotColor.Gray100
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { Spacer(modifier = Modifier.height(6.dp)) }
            searchedShows.forEach { show ->

                // the reason it has two item just in a foreach scope is because the mock response type isn't clear
                // TODO: Remove one of item scope and assign the real data into the ShowInfo
                item {
                    ShowInfo(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        imageUrl = "https://img.hankyung.com/photo/202406/01.37069998.1.jpg",
                        "Nothing But Thieves Nothing But Thieves ",
                        "2024.12.4 (수) 오후 8시",
                        "KBS 아레나홀"
                    )
                }

                item {
                    ShowInfo(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        imageUrl = "https://img.hankyung.com/photo/202406/01.37069998.1.jpg",
                        "Dua Lipa",
                        "2024.12.4 (수) 오후 8시",
                        "KBS 아레나홀"
                    )
                }
            }
        }

    }
}