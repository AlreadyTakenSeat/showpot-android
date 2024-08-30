package com.alreadyoccupiedseat.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.IconMenuWithCount
import com.alreadyoccupiedseat.designsystem.component.ticketSlider.TicketSlidePager
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H0
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H0
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Preview
@Composable
fun PreviewNotificationScreen(modifier: Modifier = Modifier) {
    NotificationScreen(
        onLoginRequested = {},
        onMyAlarmSettingClicked = {},
        onMyFavoriteShowsClicked = {},
        onMyFinishedShowClicked = {}
    )
}

@Composable
fun NotificationScreen(
    onLoginRequested: () -> Unit,
    onMyAlarmSettingClicked: () -> Unit,
    onMyFavoriteShowsClicked: () -> Unit,
    onMyFinishedShowClicked: () -> Unit,
) {

    val viewModel = hiltViewModel<NotificationViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.getUpcomingTicketingShows()
    }

    NotificationScreenContent(
        state = state.value,
        onLoginRequested = {
            onLoginRequested()
        },
        onMyAlarmSettingClicked = {
            onMyAlarmSettingClicked()
        },
        onMyFavoriteShowsClicked = {
            onMyFavoriteShowsClicked()
        },
        onMyFinishedShowClicked = {
            onMyFinishedShowClicked()
        }
    )
}

// Todo: Need To Real Data
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationScreenContent(
    state: NotificationState,
    onLoginRequested: () -> Unit,
    onMyAlarmSettingClicked: () -> Unit,
    onMyFavoriteShowsClicked: () -> Unit,
    onMyFinishedShowClicked: () -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = { state.upcomingTicketingShows.size - 1 })
    Scaffold(
        containerColor = ShowpotColor.Gray700,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {

            item {
                Spacer(Modifier.height(6.dp))
            }

            item {
                ShowPotKoreanText_H1(
                    text = "티켓팅이 임박한 공연",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    color = ShowpotColor.Gray300
                )
            }

            item {
                Spacer(Modifier.height(1.dp))
            }

            if (state.isLoggedIn && state.upcomingTicketingShows.isNotEmpty()) {
                item {
                    ShowPotEnglishText_H0(
                        text = state.upcomingTicketingShows[pagerState.currentPage].title,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = ShowpotColor.Gray100
                    )
                }

                item {
                    Row {
                        ShowPotKoreanText_H0(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "공연 티켓팅까지, ",
                            color = ShowpotColor.Gray100,
                        )

                        ShowPotKoreanText_H0(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "D-" + daysUntil(state.upcomingTicketingShows[pagerState.currentPage].cursorValue),
                            color = ShowpotColor.MainOrange,
                        )
                    }
                }

                item {
                    TicketSlidePagerForAlarmReservedShow(pagerState, state.upcomingTicketingShows)
                }
            } else {
                item {
                    NotificationScreenEmpty(state.isLoggedIn) {
                        onLoginRequested()
                    }
                }
            }

            item {
                Spacer(Modifier.height(23.dp))
            }

            item {
                IconMenuWithCount(
                    firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_alarm_24_default),
                    title = "알림 설정한 공연",
                    count = 12,
                ) {
                    onMyAlarmSettingClicked()
                }
            }

            item {
                Spacer(Modifier.height(12.dp))
            }
            // 관심 공연
            item {
                IconMenuWithCount(
                    firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_heart_24),
                    title = stringResource(com.alreadyoccupiedseat.designsystem.R.string.favorite_shows),
                    count = 3,
                ) {
                    onMyFavoriteShowsClicked()
                }
            }

            item { Spacer(Modifier.height(12.dp)) }
            // 티켓팅 종료 공연
//            item {
//                IconMenuWithCount(
//                    firstIcon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_ticket_close_24), // Todo: Change Icon (?) -> 작아보임
//                    title = stringResource(com.alreadyoccupiedseat.designsystem.R.string.close_ticketing_shows),
//                    count = 5,
//                ) {
//                    onMyFinishedShowClicked()
//                }
//            }
        }

    }
}