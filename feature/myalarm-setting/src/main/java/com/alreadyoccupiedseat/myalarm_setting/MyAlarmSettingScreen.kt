package com.alreadyoccupiedseat.myalarm_setting

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.common.infinitescroll.InfinityLazyColumn
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.TicketingNotificationBottomSheet
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton
import com.alreadyoccupiedseat.model.show.ShowType
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyAlertSettingScreen(
    navController: NavController,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit,
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<MyAlertSettingViewModel>()
    val state = viewModel.collectAsState()
    val alertSuccess = stringResource(id = R.string.my_alert_success)

    viewModel.collectSideEffect {
        when (it) {
            is MyAlertSettingEvent.Idle -> {
                Log.d("MyAlarmSettingScreen", "Idle")
            }

            is MyAlertSettingEvent.AlertRegisterSuccess -> {
                Toast.makeText(
                    context,
                    alertSuccess,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAlertReservedShow()
    }

    LaunchedEffect(state.value.isLoggedIn) {
        if (state.value.isLoggedIn) viewModel.getAlertReservedShow()
    }

    LaunchedEffect(state.value.selectedShowId) {
        if (state.value.isLoggedIn) {
            viewModel.checkAlertReservation(
                state.value.selectedShowId ?: "",
                ShowType.NORMAL.name
            )
        }
    }

    MyAlertSettingScreenContent(
        modifier = Modifier,
        state = state.value,
        onBackClicked = {
            navController.popBackStack()
        },
        loadMore = {
            viewModel.loadNextPage()
        },
        onShowClicked = { id ->
            onShowClicked(id)
        },
        onEntireShowClicked = {
            onEntireShowClicked()
        },
        onTicketSheetVisible = { isVisible ->
            viewModel.setAlertSheetVisible(isVisible)
        },
        onAlertOptionSheetVisible = { isVisible ->
            viewModel.setAlertOptionSheetVisible(isVisible)
        },
        onSelectedShowId = { id ->
            viewModel.setSelectedShowId(id)
        },
        onClearAlertClicked = {
            viewModel.clearNotification()
        },
        onChangeAlertSheetVisibility = { isVisible ->
            viewModel.setAlertSheetVisible(isVisible)
        },
        onTicketingSelectionBoxClicked = {
            viewModel.changeTicketingSelectionBoxState(it)
        },
        onRegisterAlertButtonClicked = {
            viewModel.registerTicketingAlert()
        }
    )
}

@Composable
fun MyAlertSettingScreenContent(
    modifier: Modifier,
    state: MyAlertSettingState,
    onBackClicked: () -> Unit,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit,
    onTicketSheetVisible: (Boolean) -> Unit,
    onAlertOptionSheetVisible: (Boolean) -> Unit,
    onSelectedShowId: (String) -> Unit,
    onClearAlertClicked: () -> Unit,
    onChangeAlertSheetVisibility: (Boolean) -> Unit,
    onTicketingSelectionBoxClicked: (Int) -> Unit = {},
    onRegisterAlertButtonClicked: () -> Unit,
    loadMore: () -> Unit = {},
) {

    if (state.isAlertOptionSheetVisible) {
        AlarmOptionsBottomSheet(
            onTicketSheetVisible = {
                onTicketSheetVisible(true)
            },
            onDismissRequest = {
                onAlertOptionSheetVisible(false)
            },
            onClearAlertClicked = {
                onClearAlertClicked()
            }
        )
    }

    if (state.isAlertSheetVisible) {
        TicketingNotificationBottomSheet(
            ticketingBoxSelectionState = state.ticketingBoxSelectionState,
            onSelectionBoxClicked = {
                onTicketingSelectionBoxClicked(it)
            },
            onMainButtonClicked = {
                onRegisterAlertButtonClicked()
                onChangeAlertSheetVisibility(false)
            },
            onDismissRequested = {
                onChangeAlertSheetVisibility(false)
            })
    }

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            MyAlarmSettingTopBar(onBackClicked = onBackClicked)
        },
        content = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(top = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.alertReservedShowList.isEmpty()) {
                    MyAlarmEmpty(onEntireShowClicked = onEntireShowClicked)
                } else {
                    InfinityLazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize(),
                        loadMore = {
                            loadMore()
                        },
                        loadMoreLimitCount = 7
                    ) {
                        items(state.alertReservedShowList) { show ->
                            ShowInfo(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        onShowClicked(show.id)
                                    },
                                imageUrl = show.imageURL,
                                showTitle = show.title,
                                dateInfo = show.startAt.replace("-", "."),
                                locationInfo = show.location,
                                icon = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .background(ShowpotColor.Gray500)
                                            .clickable {
                                                onSelectedShowId(show.id)
                                                onAlertOptionSheetVisible(true)
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
        }
    )
}

@Composable
fun MyAlarmEmpty(onEntireShowClicked: () -> Unit) {
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
                onEntireShowClicked()
            }
        )
    }
}

