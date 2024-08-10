package com.alreadyoccupiedseat.myalarm_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.ShowPotTopBar
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun MyAlarmSettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

}

@Composable
fun MyAlarmSettingScreenContent(
    modifier: Modifier,
    onBackClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var isSheetVisible by remember { mutableStateOf(false) }

    AlarmOptionsBottomSheet(
        onDismissRequest = { isSheetVisible = false }
    )

    if (isSheetVisible) {
        AlarmOptionsBottomSheet(
            onDismissRequest = { isSheetVisible = false }
        )
    }

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            MyAlarmSettingToaBar(
                onBackClicked = {
                    onBackClicked()
                }
            )
        },

        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(top = 12.dp)
                    .padding(it),
            ) {
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
                        imageUrl = "https://thumb.mt.co.kr/06/2024/04/2024040913332068429_1.jpg/dims/optimize/",
                        "Dua Lipa",
                        "2024.12.4 (수) 오후 8시",
                        "KBS 아레나홀"
                    )
                }
            }
        }
    )
}

@Composable
fun MyAlarmSettingToaBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    ShowPotTopBar(
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
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

@Composable
fun AlarmOptionsBottomSheet(onDismissRequest: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SheetHandler()

        ShowPotMainButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .background(ShowpotColor.Gray400),
            text = stringResource(id = R.string.action_change),
            onClicked = {
                // TODO : Change Alarm
            }
        )

        ShowPotMainButton(
            modifier = Modifier
                .padding(top = 12.dp)
                .background(ShowpotColor.Gray400),
            text = stringResource(id = R.string.action_turn_off),
            onClicked = {
                // TODO : Turn Off Alarm
            }
        )

        Spacer(modifier = Modifier.height(54.dp))
    }
}
