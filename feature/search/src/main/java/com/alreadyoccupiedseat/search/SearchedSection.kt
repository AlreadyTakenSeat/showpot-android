package com.alreadyoccupiedseat.search

import androidx.compose.foundation.clickable
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
import com.alreadyoccupiedseat.core.extension.EMPTY
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
import com.alreadyoccupiedseat.model.SubscribedArtist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchedSection(
    isLoggedIn: Boolean,
    isLoginSheetVisible: Boolean,
    isArtistUnSubscriptionSheetVisible: Boolean,
    searchedArtists: List<SubscribedArtist>,
    searchedShows: List<SearchedShow>,
    unSubscribeTargetArtist: SubscribedArtist?,
    onLoginSheetVisibilityChanged: (Boolean) -> Unit = {},
    onUnSubscribeTargetArtistChanged: (SubscribedArtist) -> Unit = {},
    onShowClicked: (String) -> Unit = {},
    onArtistUnSubscriptionSheetVisibilityChanged: (Boolean) -> Unit = {},
    onSubscribeArtist: (String) -> Unit = {},
    onUnSubscribeArtist: () -> Unit = {},
    onLoginRequested: () -> Unit = {},
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
                    text = unSubscribeTargetArtist?.name ?: String.EMPTY,
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
                    onUnSubscribeArtist()
                    onArtistUnSubscriptionSheetVisibilityChanged(false)
                }

                Spacer(modifier = Modifier.height(54.dp))
            }
        }

    }

    if (isLoginSheetVisible) {
        ShowPotBottomSheet(
            onDismissRequest = {
                onLoginSheetVisibilityChanged(false)
            },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SheetHandler()

                ShowPotKoreanText_H1(
                    modifier = Modifier.fillMaxWidth(),
                    text = "로그인 후 좋아하는\n" +
                            "아티스트 구독을 해보세요!",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(19.dp))

                ShowPotMainButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "3초만에 로그인하기"
                ) {
                    onLoginRequested()
                    onLoginSheetVisibilityChanged(false)
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
                        text = artist.name,
                        isSubscribed = artist.isSubscribed,
                    ) {
                        if (isLoggedIn) {
                            if (artist.isSubscribed) {
                                onUnSubscribeTargetArtistChanged(artist)
                                onArtistUnSubscriptionSheetVisibilityChanged(true)
                            } else {
                                // TODO: current it's null
                                onSubscribeArtist(artist.id)
                            }
                        } else {
                            onLoginSheetVisibilityChanged(true)
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

                item {
                    ShowInfo(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .clickable {
                                onShowClicked(show.id)
                            },
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