package com.alreadyoccupiedseat.subscription_artist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.artist.ShowPotArtistSubscription
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.SheetHandler
import com.alreadyoccupiedseat.designsystem.component.bottomSheet.ShowPotBottomSheet
import com.alreadyoccupiedseat.designsystem.component.snackbar.CheckIconSnackbar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import com.alreadyoccupiedseat.model.Artist
import kotlinx.coroutines.launch

@Composable
fun SubscriptionArtistScreen(
    navController: NavController,
    onGoToSeeClicked: () -> Unit = {},
    onLoginRequested: () -> Unit = {},
) {

    val viewModel = hiltViewModel<SubscriptionArtistViewModel>()
    val state = viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.event.collect {
            when (it) {
                SubscriptionArtistScreenEvent.Idle -> {

                }
                SubscriptionArtistScreenEvent.SubscribeArtistsSuccess -> {
                    snackbarHostState.showSnackbar("구독 설정이 완료되었습니다")
                }
            }
        }
    }


    SubscriptionArtistScreenContent(
        state = state.value,
        snackbarHostState = snackbarHostState,
        onBackClicked = {
            navController.popBackStack()
        },
        onSheetStateChanged = { isVisible ->
            viewModel.setSheetVisible(isVisible)
        },
        onSubscribeButtonClicked = {
            viewModel.subscribeArtists()
        },
        onArtistClicked = {
            viewModel.selectArtist(it)
        },
        checkIsSelected = {
            viewModel.isSelected(it)
        },
        onLoginRequested = {
            viewModel.setSheetVisible(false)
            onLoginRequested()
        },
        onGoToSeeClicked = {
            onGoToSeeClicked()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SubscriptionArtistScreenContent(
    state: SubscriptionArtistScreenState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackClicked: () -> Unit,
    onSheetStateChanged: (Boolean) -> Unit = {},
    onSubscribeButtonClicked: () -> Unit = {},
    onArtistClicked: (Artist) -> Unit = {},
    checkIsSelected: (Artist) -> Boolean,
    onLoginRequested: () -> Unit = {},
    onGoToSeeClicked: () -> Unit = {},
) {

    val scope = rememberCoroutineScope()

    if (state.isSheetVisible) {

        ShowPotBottomSheet(
            onDismissRequest = {
                onSheetStateChanged(false)
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
                    onGoToSeeClicked()
                }
            }
        },
        // TODO: To be a component
        topBar = {
            SubscriptionArtistTopBar(
                onBackClicked = {
                    onBackClicked()
                }
            )
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

                    items(state.unsubscribedArtists.size) { index ->
                        val curArtist = state.unsubscribedArtists[index]
                        ShowPotArtistSubscription(
                            text = curArtist.name,
                            imageUrl = curArtist.imageURL,
                            isSelected = checkIsSelected(curArtist),
                        ) {
                            if (state.isLoggedIn.not()) {
                                onSheetStateChanged(true)
                            } else {
                                onArtistClicked(curArtist)
                            }
                        }
                    }

                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    ShowpotColor.Gray700.copy(alpha = 0f),
                                    ShowpotColor.Gray700
                                ),
                            )
                        ).align(Alignment.BottomCenter)
                )

                this@Column.AnimatedVisibility(
                    visible = state.selectedArtists.isNotEmpty(),
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight }
                    )
                ) {
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
                            }
                        }
                    }
                }
            }
        }
    }
}