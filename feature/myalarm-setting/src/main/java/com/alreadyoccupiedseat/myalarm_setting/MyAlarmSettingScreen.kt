package com.alreadyoccupiedseat.myalarm_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.TicketingNotificationBottomSheet
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton

@Composable
fun MyAlarmSettingScreen(
    navController: NavController,
) {

    val viewModel = hiltViewModel<MyAlarmSettingViewModel>()
    val state = viewModel.state.collectAsState()
    MyAlarmSettingScreenContent(
        modifier = Modifier,
        state = state.value,
        onBackClicked = {
            navController.popBackStack()
        },
        onDismissRequested = {

        },
        onTicketSheetVisible = { isVisible ->
            viewModel.setTicketSheetVisible(isVisible)
        },
        onAlarmOptionSheetVisible = { isVisible ->
            viewModel.setAlarmOptionSheetVisible(isVisible)
        },
        onSelectedShowId = { id ->
            viewModel.setSelectedShowId(id)
        },
        onRemoveClicked = {
              viewModel.removeClicked()
        },
        onFirstItemClicked = { isFirstItemSelected ->
            viewModel.setFirstItemSelected(isFirstItemSelected)
        },
        onSecondItemClicked = { isSecondItemSelected ->
            viewModel.setSecondItemSelected(isSecondItemSelected)
        },
        onThirdItemClicked = { isThirdItemSelected ->
            viewModel.setThirdItemSelected(isThirdItemSelected)
        }
    )
}

@Composable
fun MyAlarmSettingScreenContent(
    modifier: Modifier,
    state: MyAlarmSettingState,
    onBackClicked: () -> Unit,
    onDismissRequested: () -> Unit,
    onTicketSheetVisible: (Boolean) -> Unit,
    onAlarmOptionSheetVisible: (Boolean) -> Unit,
    onSelectedShowId: (String) -> Unit,
    onRemoveClicked: () -> Unit,
    onFirstItemClicked: (Boolean) -> Unit,
    onSecondItemClicked: (Boolean) -> Unit,
    onThirdItemClicked: (Boolean) -> Unit,
) {


    if (state.isAlarmOptionSheetVisible) {
        AlarmOptionsBottomSheet(
            onTicketSheetVisible = {
                onTicketSheetVisible(true)
            },
            onDismissRequest = {
                onAlarmOptionSheetVisible(false)
            },
            onRemoveClicked = {
                onRemoveClicked()
            }
        )
    }

    if (state.isTicketSheetVisible) {
        TicketingNotificationBottomSheet(
            firstItemSelected = state.isFirstItemSelected,
            secondItemSelected = state.isSecondItemSelected,
            thirdItemSelected = state.isThirdItemSelected,
            onFirstItemClicked = {
                onFirstItemClicked(!state.isFirstItemSelected)
            },
            onSecondItemClicked = {
                onSecondItemClicked(!state.isSecondItemSelected)
            },
            onThirdItemClicked = {
                onThirdItemClicked(!state.isThirdItemSelected)
            },
            onMainButtonClicked = {
                // TODO: Implement
            },
            onDismissRequested = {
                onTicketSheetVisible(false)
                onAlarmOptionSheetVisible(false)
            })
    }

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            MyAlarmSettingTopBar(onBackClicked = onBackClicked)
        },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(top = 12.dp)
                    .padding(it),
            ) {
                val alarmReservedShow = state.alarmReservedShow
                if (alarmReservedShow.isEmpty()) {
                    item {
                        MyAlarmEmpty()
                    }
                } else {
                    itemsIndexed(alarmReservedShow) { index, show ->
                        ShowInfo(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    // TODO 공연 상세 페이지 이동
                                },
                            imageUrl = show.imageURL,
                            showTitle = show.title,
                            dateInfo = show.cursorValue,
                            locationInfo = show.location,
                            icon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .background(ShowpotColor.Gray500)
                                        .clickable {
                                            onSelectedShowId(show.id)
                                            onAlarmOptionSheetVisible(true)
                                        }
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(start = 5.dp)
                                            .padding(vertical = 5.dp),
                                        painter = painterResource(R.drawable.ic_alarm_24_default),
                                        contentDescription = null,
                                        tint = ShowpotColor.White
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = 5.dp)
                                            .padding(vertical = 5.dp),
                                        painter = painterResource(R.drawable.ic_arrow_24_down),
                                        contentDescription = null,
                                        tint = ShowpotColor.Gray300
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun MyAlarmEmpty() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxSize()
    ) {

        DefaultScreenWhenEmpty(
            imageResId = R.drawable.img_empty_alarm,
            text = stringResource(id = R.string.no_alarm_show)
        )

        Spacer(modifier = Modifier.height(96.dp))

        ShowPotSubButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.action_show_info),
            onClicked = {
                // TODO 공연 찾기 페이지 이동
            }
        )
    }
}

