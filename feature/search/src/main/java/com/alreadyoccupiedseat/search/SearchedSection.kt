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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.artist.ShowPotArtistAlarm
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import com.alreadyoccupiedseat.model.SearchedShow
import com.alreadyoccupiedseat.model.Show
import com.alreadyoccupiedseat.model.SubscribedArtist

// TODO: ShowInfo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchedSection(
    isArtistUnSubscriptionSheetVisible: Boolean,
    searchedArtists: List<SubscribedArtist>,
    searchedShows: List<SearchedShow>,
    unSubscribeTargetArtist: String,
    onUnSubscribeTargetArtistChanged: (String) -> Unit = {},
    onArtistUnSubscriptionSheetVisibilityChanged: (Boolean) -> Unit = {}
) {

    if (isArtistUnSubscriptionSheetVisible) {

        ShowPotBottomSheet(
            onDismissRequest = {
                onArtistUnSubscriptionSheetVisibilityChanged(false)
            },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SheetHandler()

                ShowPotEnglishText_H1(
                    modifier = Modifier.fillMaxWidth(),
                    text = unSubscribeTargetArtist,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(1.dp))

                ShowPotKoreanText_H1(
                    modifier = Modifier.fillMaxWidth(),
                    text = "구독을 취소하시겠습니까?",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(19.dp))

                ShowPotMainButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "구독 취소하기"
                ) {

                }

                Spacer(modifier = Modifier.height(54.dp))
            }
        }

    }

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
            searchedArtists.forEach { artist ->
                item {
                    ShowPotArtistAlarm(
                        imageUrl = artist.imageURL,
                        text = artist.englishName,
                        isSubscribed = artist.isSubscribed,
                    ) {
                        if (artist.isSubscribed) {
                            onUnSubscribeTargetArtistChanged(artist.englishName)
                            onArtistUnSubscriptionSheetVisibilityChanged(true)
                        } else {
                            // TODO: subscribe
                        }
                    }
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
                        imageUrl = show.imageURL,
                        show.title,
                        show.startAt.replace("-", "."),
                        show.location
                    )
                }

            }
        }

    }
}