package com.alreadyoccupiedseat.show_detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.core.extension.isScrollingUp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.GenreChip
import com.alreadyoccupiedseat.designsystem.component.artist.ShowPotArtist
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.TicketingNotificationBottomSheet
import com.alreadyoccupiedseat.designsystem.component.button.LabelButton
import com.alreadyoccupiedseat.designsystem.component.button.IconButtonWithShowPotMainButton
import com.alreadyoccupiedseat.designsystem.getTicketSiteButtonColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H0
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun ShowDetailScreen(
    navController: NavController,
    showId: String
) {

    val viewModel = hiltViewModel<ShowDetailViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(showId) {
        viewModel.getShowDetail(showId)
    }

    ShowDetailScreenContent(
        state = state.value,
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onIconButtonClicked = {
            viewModel.registerShowInterest(showId)
        }
    )
}

@Composable
fun ShowDetailScreenContent(
    state: ShowDetailState,
    onBackButtonClicked: () -> Unit,
    onIconButtonClicked: () -> Unit,
) {

    val lazyColumnState = rememberLazyListState()

    val backgroundColor by animateColorAsState(
        targetValue = if (lazyColumnState.isScrollingUp()
                .not()
        ) ShowpotColor.Gray700 else Color.Transparent
    )

    var isSheetVisible by remember { mutableStateOf(true) }
    var isFirstItemSelected by remember { mutableStateOf(false) }
    var isSecondItemSelected by remember { mutableStateOf(false) }
    var isThirdItemSelected by remember { mutableStateOf(false) }

    if (isSheetVisible) {

        TicketingNotificationBottomSheet(
            firstItemSelected = isFirstItemSelected,
            secondItemSelected = isSecondItemSelected,
            thirdItemSelected = isThirdItemSelected,
            onFirstItemClicked = {
                isFirstItemSelected = !isFirstItemSelected
            },
            onSecondItemClicked = {
                isSecondItemSelected = !isSecondItemSelected
            },
            onThirdItemClicked = {
                isThirdItemSelected = !isThirdItemSelected
            },
            onMainButtonClicked = {
                // TODO: Implement
            },
            onDismissRequested = {
                isSheetVisible = false
            })

    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ShowpotColor.Gray700,
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = androidx.compose.ui.Alignment.TopStart
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 118.dp),
                state = lazyColumnState,
            ) {
                item {
                    // Show Image
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(524.dp),
                        model = state.showDetail?.posterImageURL,
                        contentDescription = "show image",
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    )
                }

                item {
                    ShowPotEnglishText_H0(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                        state.showDetail?.name ?: "",
                        color = Color.White
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(ShowpotColor.Gray500)
                    )
                }

                item {
                    HorizontalTitleAndInfoText(
                        Modifier.padding(horizontal = 16.dp),
                        "기간",
                        state.showDetail?.startDate?.replace("-", ".") ?: String.EMPTY
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )
                }

                // TODO: Model should be updated
                item {
                    HorizontalTitleAndInfoText(
                        Modifier.padding(horizontal = 16.dp),
                        "장소",
                        state.showDetail?.location ?: String.EMPTY
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(vertical = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(ShowpotColor.Gray500)
                    )
                }

                item {
                    ShowPotKoreanText_H2(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp),
                        text = "티켓팅 정보",
                        color = Color.White,
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                            8.dp
                        )
                    ) {

                        state.showDetail?.ticketingSites?.forEach {
                            item {
                                LabelButton(
                                    backgroundColor = it.name.getTicketSiteButtonColor(),
                                    text = it.name,
                                )
                            }
                        }
                    }
                }

                // TODO: After MVP
//                item {
//                    HorizontalTitleAndInfoText(
//                        Modifier.padding(horizontal = 16.dp),
//                        "선예매 오픈",
//                        "6월 20일 (목) 12:00"
//                    )
//                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }

                item {
                    HorizontalTitleAndInfoText(
                        Modifier.padding(horizontal = 16.dp),
                        "일반예매 오픈",
                        state.showDetail?.startDate?.replace("-", ".") ?: String.EMPTY
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(top = 14.dp, bottom = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(ShowpotColor.Gray500)
                    )
                }

                item {
                    ShowPotKoreanText_H2(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp),
                        text = "아티스트 정보",
                        color = Color.White,
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                            18.dp
                        )
                    ) {
                        state.showDetail?.artists?.forEach { artist ->
                            item {
                                ShowPotArtist(
                                    text = artist.englishName,
                                    imageUrl = artist.imageURL,
                                )
                            }
                        }

                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(ShowpotColor.Gray500)
                    )
                }

                item {
                    ShowPotKoreanText_H2(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp),
                        text = "좌석 가격 정보",
                        color = Color.White,
                    )
                }

                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .background(ShowpotColor.Gray600)
                            .padding(12.dp),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                            4.dp
                        )
                    ) {

                        state.showDetail?.seats?.forEach {
                            HorizontalTitleAndInfoText(
                                title = it.seatType,
                                infoText = it.price.toString() + "원"
                            )
                        }
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(top = 14.dp)
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(ShowpotColor.Gray500)
                    )
                }

                item {
                    ShowPotKoreanText_H2(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(bottom = 12.dp),
                        text = "공연 장르",
                        color = Color.White,
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement =
                        androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        state.showDetail?.genres?.forEach {
                            item {
                                GenreChip(genre = it.name)
                            }
                        }
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(36.dp)
                    )
                }

            }

            // Topbar Section
            Column(
                modifier = Modifier.fillMaxWidth()
                    .height(153.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                ShowpotColor.Gray800.copy(alpha = 0.5f),
                                ShowpotColor.Gray800.copy(alpha = 0f),
                            ),
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .padding(top = 12.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp, bottom = 4.dp)
                            .clickable {
                                onBackButtonClicked()
                            },
                        painter = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_arrow_36_left),
                        contentDescription = "backButton",
                        tint = Color.White,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    ShowPotKoreanText_H1(
                        text = stringResource(R.string.show_information),
                        color = Color.White,
                    )
                }
            }

            // CTA Button Container
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                IconButtonWithShowPotMainButton(
                    if (state.showDetail?.isInterested == true) painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.ic_heart_36_off)
                    else painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.ic_heart_36_on),
                    stringResource(R.string.set_notification),
                    onIconButtonClicked = {
                        onIconButtonClicked()
                    },
                    onMainButtonClicked = {

                    }
                )

            }
        }
    }
}