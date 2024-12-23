package com.alreadyoccupiedseat.show_detail

import android.widget.Toast
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.artist.ShowPotArtist
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.TicketingNotificationBottomSheet
import com.alreadyoccupiedseat.designsystem.component.button.LabelButton
import com.alreadyoccupiedseat.designsystem.component.button.IconButtonWithShowPotMainButton
import com.alreadyoccupiedseat.designsystem.getTicketSiteButtonColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H0
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ShowDetailScreen(
    navController: NavController,
    showId: String,
    onTicketingButtonClicked: (String) -> Unit,
    onLoginRequested: () -> Unit
) {

    val viewModel = hiltViewModel<ShowDetailViewModel>()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is ShowDetailEvent.Idle -> {


                }

                is ShowDetailEvent.AlertRegisterSuccess -> {
                    Toast.makeText(
                        context,
                        "알림 요청에 성공하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    LaunchedEffect(showId) {
        viewModel.getShowDetail(showId)
        viewModel.registerShowId(showId)
        viewModel.checkLogin()
    }

    LaunchedEffect(state.value.isLoggedIn) {
        if (state.value.isLoggedIn) {
            // viewModel.checkAlertAvailability(showId)
        }
    }

    ShowDetailScreenContent(
        state = state.value,
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onIconButtonClicked = {
            viewModel.registerShowInterest(showId)
        },
        onChangeAlertSheetVisibility = {
            viewModel.changeAlertSheetVisibility(it)
        },
        onLoginSheetVisibilityChanged = {
            viewModel.changeLoginSheetVisibility(it)
        },
        onFirstItemClicked = {
            viewModel.changeFirstItemSelection()
        },
        onSecondItemClicked = {
            viewModel.changeSecondItemSelection()
        },
        onThirdItemClicked = {
            viewModel.changeThirdItemSelection()
        },
        onRegisterAlertButtonClicked = {
            viewModel.registerTicketingAlert(
                showId,
                "NORMAL",
                mutableListOf<String>().apply {
                    with(state.value) {
                        if (isFirstItemSelected) this@apply.add(TicketingAlertTime.BEFORE_24.name)
                        if (isSecondItemSelected) this@apply.add(TicketingAlertTime.BEFORE_6.name)
                        if (isThirdItemSelected) this@apply.add(TicketingAlertTime.BEFORE_1.name)
                    }
                }
            )
        },
        onTicketingButtonClicked = {
            onTicketingButtonClicked(it)
        },
        onLoginRequested = {
            onLoginRequested()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreenContent(
    state: ShowDetailState,
    onBackButtonClicked: () -> Unit,
    onIconButtonClicked: () -> Unit,
    onChangeAlertSheetVisibility: (Boolean) -> Unit,
    onFirstItemClicked: () -> Unit,
    onSecondItemClicked: () -> Unit,
    onThirdItemClicked: () -> Unit,
    onRegisterAlertButtonClicked: () -> Unit,
    onLoginSheetVisibilityChanged: (Boolean) -> Unit = {},
    onLoginRequested: () -> Unit = {},
    onTicketingButtonClicked: (String) -> Unit
) {

    val lazyColumnState = rememberLazyListState()

    val backgroundColor by animateColorAsState(
        targetValue = if (lazyColumnState.isScrollingUp()
                .not()
        ) ShowpotColor.Gray700 else Color.Transparent
    )

    if (state.isAlertSheetVisible) {

        TicketingNotificationBottomSheet(
            isFirstItemAvailable = state.isFirstItemAvailable,
            isSecondItemAvailable = state.isSecondItemAvailable,
            isThirdItemAvailable = state.isThirdItemAvailable,
            firstItemSelected = state.isFirstItemSelected,
            secondItemSelected = state.isSecondItemSelected,
            thirdItemSelected = state.isThirdItemSelected,
            onFirstItemClicked = {
                onFirstItemClicked()
            },
            onSecondItemClicked = {
                onSecondItemClicked()
            },
            onThirdItemClicked = {
                onThirdItemClicked()
            },
            onMainButtonClicked = {
                onRegisterAlertButtonClicked()
                onChangeAlertSheetVisibility(false)
            },
            onDismissRequested = {
                onChangeAlertSheetVisibility(false)
            })

    } else if (state.isLoginSheetVisible) {
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
                                ) {
                                    onTicketingButtonClicked(it.link)
                                }
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
                        state.showDetail?.ticketingTimes?.first()?.ticketingAt?.formatToReservationDate()
                            ?: String.EMPTY
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
                                    text = artist.name,
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
                                infoText = it.price.toFormattedString() + "원"
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
                    if (state.showDetail?.isInterested == true) painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.ic_heart_36_on)
                    else painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.ic_heart_36_off),
                    stringResource(R.string.set_notification),
                    onIconButtonClicked = {
                        if (state.isLoggedIn) onIconButtonClicked()
                        else onLoginSheetVisibilityChanged(true)

                    },
                    onMainButtonClicked = {
                        if (state.isLoggedIn) onChangeAlertSheetVisibility(true)
                        else onLoginSheetVisibilityChanged(true)
                    }
                )
            }
        }
    }
}

internal fun Int.toFormattedString(): String {
    return "%,d".format(this)
}

internal fun String.formatToReservationDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm")
    val dateTime = LocalDateTime.parse(this, inputFormatter)

    val outputFormatter = DateTimeFormatter.ofPattern("M월 d일 (E) HH:mm")

    val dayOfWeek = dateTime.dayOfWeek
    val koreanDayOfWeek = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)

    return dateTime.format(outputFormatter).replace(dayOfWeek.name, koreanDayOfWeek)
}