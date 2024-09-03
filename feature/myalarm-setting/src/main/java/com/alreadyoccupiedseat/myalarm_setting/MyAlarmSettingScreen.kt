package com.alreadyoccupiedseat.myalarm_setting

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.alreadyoccupiedseat.enum.TicketingAlertTime
import com.alreadyoccupiedseat.model.show.Shows.Companion.NORMAL
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyAlarmSettingScreen(
    navController: NavController,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<MyAlarmSettingViewModel>()
    val state = viewModel.state.collectAsState()
    val alertSuccess = stringResource(id = R.string.my_alert_success)

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is MyAlarmSettingEvent.Idle -> {
                    Log.d("MyAlarmSettingScreen", "Idle")
                }
                is MyAlarmSettingEvent.AlertRegisterSuccess -> {
                    Toast.makeText(
                        context,
                        alertSuccess,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAlarmReservedShow()
    }

    MyAlarmSettingScreenContent(
        modifier = Modifier,
        state = state.value,
        onBackClicked = {
            navController.popBackStack()
        },
        onDismissRequested = {
            viewModel.setAlarmOptionSheetVisible(false)
            viewModel.setTicketSheetVisible(false)
        },
        onShowClicked = { id ->
            onShowClicked(id)
        },
        onEntireShowClicked = {
            onEntireShowClicked()
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
        onFirstItemClicked = {
            viewModel.changeFirstItemSelection()
        },
        onSecondItemClicked = {
            viewModel.changeSecondItemSelection()
        },
        onThirdItemClicked = {
            viewModel.changeThirdItemSelection()
        },
        onCheckAlertAvailability = {
            viewModel.checkAlertAvailability()
        },
        onRegisterAlertButtonClicked = {
            viewModel.registerTicketingAlert(
                NORMAL,
                mutableListOf<String>().apply {
                    with(state.value) {
                        if (isFirstItemSelected) this@apply.add(TicketingAlertTime.BEFORE_24.name)
                        if (isSecondItemSelected) this@apply.add(TicketingAlertTime.BEFORE_6.name)
                        if (isThirdItemSelected) this@apply.add(TicketingAlertTime.BEFORE_1.name)
                    }
                }
            )
        }
    )
}

@Composable
fun MyAlarmSettingScreenContent(
    modifier: Modifier,
    state: MyAlarmSettingState,
    onBackClicked: () -> Unit,
    onDismissRequested: () -> Unit,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit,
    onTicketSheetVisible: (Boolean) -> Unit,
    onAlarmOptionSheetVisible: (Boolean) -> Unit,
    onSelectedShowId: (String) -> Unit,
    onRemoveClicked: () -> Unit,
    onFirstItemClicked: () -> Unit,
    onSecondItemClicked: () -> Unit,
    onThirdItemClicked: () -> Unit,
    onCheckAlertAvailability: () -> Unit,
    onRegisterAlertButtonClicked: () -> Unit,
) {


    if (state.isAlarmOptionSheetVisible) {
        AlarmOptionsBottomSheet(
            onTicketSheetVisible = {
                onTicketSheetVisible(true)
                onCheckAlertAvailability()
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
                onTicketSheetVisible(false)
            },
            onDismissRequested = {
                onDismissRequested()
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
                        MyAlarmEmpty(onEntireShowClicked = onEntireShowClicked)
                    }
                } else {
                    itemsIndexed(alarmReservedShow) { _, show ->
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

