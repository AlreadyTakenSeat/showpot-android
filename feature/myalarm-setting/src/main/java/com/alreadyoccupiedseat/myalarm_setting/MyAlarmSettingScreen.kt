package com.alreadyoccupiedseat.myalarm_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.ShowPotEmpty
import com.alreadyoccupiedseat.designsystem.component.ShowPotTopBar
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.TicketingNotificationBottomSheet
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun MyAlarmSettingScreen(
    navController: NavController,
) {
    MyAlarmSettingScreenContent(
        modifier = Modifier,
        onBackClicked = {
            navController.popBackStack()
        }
    )
}

@Composable
fun MyAlarmSettingScreenContent(
    modifier: Modifier,
    onBackClicked: () -> Unit,
    viewModel: MyAlarmSettingViewModel = hiltViewModel(), // ViewModel 사용
) {
    var isAlarmOptionSheetVisible by remember { mutableStateOf(false) }
    var isTicketSheetVisible by remember { mutableStateOf(false) }
    var selectedShowId by remember { mutableStateOf<String?>(null) }
    var isFirstItemSelected by remember { mutableStateOf(false) }
    var isSecondItemSelected by remember { mutableStateOf(false) }
    var isThirdItemSelected by remember { mutableStateOf(false) }


    if (isAlarmOptionSheetVisible) {
        AlarmOptionsBottomSheet(
            onTicketSheetVisible = {
                isTicketSheetVisible = true
            },
            onDismissRequest = {
                isAlarmOptionSheetVisible = false
            },
            onRemoveClicked = {
                selectedShowId?.let { id ->
                    viewModel.removeShowById(id)
                }
                isAlarmOptionSheetVisible = false
            }
        )
    }

    if (isTicketSheetVisible) {
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
                isTicketSheetVisible = false
                isAlarmOptionSheetVisible = false
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
                if (viewModel.showList.value.isEmpty()) {

                    item {
                        MyAlarmEmpty()
                    }

                } else {
                    itemsIndexed(viewModel.showList.value) { index, show ->
                        ShowInfo(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    // TODO 공연 상세 페이지 이동
                                },
                            imageUrl = show.posterImageURL,
                            showTitle = show.name,
                            dateInfo = "2024.12.$index (수) 오후 $index 시",
                            locationInfo = "KBS 아레나홀",
                            icon = {
                                Row(
                                    modifier = Modifier
                                        .background(ShowpotColor.Gray500)
                                        .clickable {
                                            selectedShowId = show.id
                                            isAlarmOptionSheetVisible = true
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
fun MyAlarmSettingTopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    ShowPotTopBar(
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    tint = ShowpotColor.White,
                    modifier = Modifier.padding(1.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_36_left),
                    contentDescription = "Back"
                )
            }
        },
        title = {
            ShowPotKoreanText_H1(
                text = stringResource(id = R.string.alarm_settings_show),
                color = ShowpotColor.Gray300,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .padding(vertical = 7.dp)
            )
        },
        contentColor = ShowpotColor.Gray700,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmOptionsBottomSheet(
    onTicketSheetVisible: () -> Unit,
    onDismissRequest: () -> Unit,
    onRemoveClicked: () -> Unit,
) {
    ShowPotBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SheetHandler()

            ShowPotKoreanText_H1(
                text = stringResource(id = R.string.alarm),
                color = ShowpotColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            ShowPotSubButton(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(id = R.string.action_change),
                onClicked = {
                    onTicketSheetVisible()
                    onDismissRequest()
                }
            )

            ShowPotSubButton(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(id = R.string.action_turn_off),
                onClicked = {
                    onRemoveClicked()
                }
            )

            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}

@Composable
fun MyAlarmEmpty(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxSize()
    ) {

        ShowPotEmpty(
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

