package com.alreadyoccupiedseat.subscription_artist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistSubscription
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.component.snackbar.CheckIconSnackbar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import kotlinx.coroutines.launch

@Composable
fun SubscriptionArtistScreen(
    navController: NavController,
) {

    val viewModel = hiltViewModel<SubscriptionArtistViewModel>()
    val event = viewModel.event.collectAsState()

    when (event.value) {
        SubscriptionArtistScreenEvent.Idle -> {
            SubscriptionArtistScreenContent(
                onBackClicked = {
                    navController.popBackStack()
                },
                onSubscribeButtonClicked = {
                    viewModel.subscribeArtists()
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionArtistScreenContent(
    onBackClicked: () -> Unit,
    onSubscribeButtonClicked: () -> Unit = {},
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // TODO: Supposed to be in a ViewModel State
    var isSheetVisible by remember { mutableStateOf(true) }

    if (isSheetVisible) {

        ShowPotBottomSheet(
            onDismissRequest = {
                isSheetVisible = false
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
                    // TODO: goto Login
                }

                Spacer(modifier = Modifier.height(54.dp))
            }
        }

    }



    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ShowpotColor.Gray700,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            ) { snackbarData ->
                CheckIconSnackbar(
                    mainText = snackbarData.visuals.message,
                    actionText = "보러가기",
                    onIconClicked = {
                        // onIconClicked()
                    },
                ) {
                    // onActionClicked()
                }
            }
        },
        // TODO: To be a component
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    painter = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_arrow_36_left),
                    contentDescription = "back",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onBackClicked()
                    }
                )

                ShowPotKoreanText_H1(
                    text = stringResource(R.string.subscribe_artist),
                    color = ShowpotColor.Gray100,
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            ShowPotKoreanText_H2(
                Modifier.padding(top = 5.dp, start = 16.dp),
                text = stringResource(R.string.select_interesting_artist),
                color = ShowpotColor.Gray300
            )

            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 27.dp)
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // TODO: Real Data
                    items(count = 21) {
                        ShowPotArtistSubscription(
                            text = "High Flying Birds",
                            icon = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.img_artist_default),
                        ) {
                            isSheetVisible = true
                        }
                    }

                }

                // Todo: Visibility depends on whether artists are selected
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .padding(bottom = 54.dp),
                ) {
                    ShowPotMainButton(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.subscribe)
                    ) {
                        scope.launch {
                            onSubscribeButtonClicked()
                            snackbarHostState.showSnackbar("구독 설정이 완료되었습니다")
                        }
                    }
                }
            }
        }
    }
}