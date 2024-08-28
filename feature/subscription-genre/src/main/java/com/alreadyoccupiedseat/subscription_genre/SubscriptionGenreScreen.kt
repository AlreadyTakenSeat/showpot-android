package com.alreadyoccupiedseat.subscription_genre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.snackbar.CheckIconSnackbar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import com.alreadyoccupiedseat.enum.GenreType
import com.alreadyoccupiedseat.model.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SubscriptionGenreScreen(
    navController: NavController,
    onLoginRequested: () -> Unit = {},
) {

    val viewModel = hiltViewModel<SubscriptionGenreViewModel>()
    val state = viewModel.state.collectAsState(SubscriptionGenreScreenState())

    SubscriptionGenreScreenContent(
        state = state.value,
        onBackClicked = {
            navController.popBackStack()
        },
        onLoginRequested = {
            onLoginRequested()
        },
        onLoginRequestSheetChange = {
            viewModel.setLoginRequestSheetVisible(it)
        },
        isSelected = {
            viewModel.isSelected(it)
        },
        onGenreClicked = {
            viewModel.selectGenre(it)
        },
        onSubscribeButtonClicked = {
            // TODO 서버와 정상 통신 시, snackBar 표기
            viewModel.subscribeGenre()
        },
        onUnsubscribeSheetChange = { isVisible ->
            viewModel.setUnSubscriptionSheetVisible(isVisible)
        },
        onUnsubscribeGenreClicked = { genre ->
            viewModel.selectUnSubscribeGenre(genre)
        },
        onUnsubscribeGenreButtonClicked = {
            viewModel.unSubscribeGenre()
        },
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionGenreScreenContent(
    state: SubscriptionGenreScreenState,
    onBackClicked: () -> Unit,
    isSelected: (Genre) -> Boolean,
    onGenreClicked: (Genre) -> Unit,
    onSubscribeButtonClicked: () -> Unit = {},
    onUnsubscribeGenreClicked: (Genre) -> Unit,
    onUnsubscribeGenreButtonClicked: () -> Unit,
    onUnsubscribeSheetChange: (Boolean) -> Unit,
    onLoginRequested: () -> Unit = {},
    onLoginRequestSheetChange: (Boolean) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    if (state.isLoggedIn.not() && state.isLoginRequestBottomSheetVisible) {
        LoginBottomSheet(
            onLoginRequested = { onLoginRequested() },
            onDismissRequest = { onLoginRequestSheetChange(false) }
        )
    }

    if (state.isUnSubscriptionSheetVisible && state.unSelectedGenre != null) {
        UnsubscribeBottomSheet(
            genreName = state.unSelectedGenre.name,
            onUnsubscribe = { onUnsubscribeGenreButtonClicked() },
            onDismissRequest = { onUnsubscribeSheetChange(false) }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            ) { snackBarData ->
                CheckIconSnackbar(
                    mainText = snackBarData.visuals.message,
                    actionText = String(),
                    onIconClicked = {
                        // onIconClicked()
                    },
                ) {
                    // onActionClicked()
                }
            }
        },
        topBar = {
            SubscriptionTopBar(onBackClicked = onBackClicked)
        },
        content = { innerPadding ->
            SubscriptionGenreContent(
                state = state,
                innerPadding = innerPadding,
                onGenreClick = { onGenreClicked(it) },
                isSelected = { isSelected(it) },
                onSubscribeButtonClicked = onSubscribeButtonClicked,
                onUnsubscribeGenreClicked = onUnsubscribeGenreClicked,
                onUnsubscribeSheetChange = onUnsubscribeSheetChange,
                snackBarHostState = snackBarHostState,
                onLoginRequestSheetChange = onLoginRequestSheetChange,
                scope = scope
            )
        }
    )
}

@Composable
fun SubscriptionGenreContent(
    scope: CoroutineScope,
    innerPadding: PaddingValues,
    state: SubscriptionGenreScreenState,
    isSelected: (Genre) -> Boolean,
    onGenreClick: (Genre) -> Unit,
    onSubscribeButtonClicked: () -> Unit,
    onUnsubscribeGenreClicked: (Genre) -> Unit,
    onUnsubscribeSheetChange: (Boolean) -> Unit,
    onLoginRequestSheetChange: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
) {

    Column(
        modifier = Modifier
            .background(ShowpotColor.Gray700)
            .padding(innerPadding)
            .fillMaxSize(),
    ) {

        ShowPotKoreanText_H2(
            modifier = Modifier.padding(start = 16.dp, top = 5.dp),
            text = stringResource(id = R.string.subscribe_genre_notification),
            color = ShowpotColor.Gray300,
        )

        Spacer(modifier = Modifier.height(14.dp).fillMaxWidth())

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyVerticalStaggeredGrid(
                state = rememberLazyStaggeredGridState(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 40.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
            ) {
                items(state.genres.size) { index ->

                    val genre = state.genres[index]
                    // UI 표기 계산
                    val isEvenIndex = index % 2 == 0
                    val align = if (isEvenIndex) Alignment.TopEnd else Alignment.TopStart
                    val topPadding = if (index == 1) 90.dp else 0.dp
                    val bottomPadding = if (index == state.genres.size - 1) 74.dp else 0.dp

                    Box(
                        modifier = Modifier
                            .padding(top = topPadding, bottom = bottomPadding)
                    ) {
                        val isLoggedIn = state.isLoggedIn
                        val displayIcon = when {
                            isLoggedIn && state.subscribedGenre.contains(genre) -> {
                                painterResource(id = GenreType.getSubscribedRes(genre.id))
                            }
                            isLoggedIn && isSelected(genre) -> {
                                painterResource(id = GenreType.getSelectedRes(genre.id))
                            }
                            else -> {
                                painterResource(id = GenreType.getNormalRes(genre.id))
                            }
                        }

                        ShowPotGenre(
                            modifier = Modifier.align(align),
                            enabled = true,
                            icon = displayIcon,
                            onSelectClicked = {
                                if (state.isLoggedIn.not()) {
                                    onLoginRequestSheetChange(true)
                                } else {
                                    state.subscribedGenre.contains(genre)
                                        .let { isSubscribed ->
                                            if (isSubscribed) {
                                                onUnsubscribeGenreClicked(genre)
                                                onUnsubscribeSheetChange(true)
                                            } else {
                                                onGenreClick(genre)
                                            }
                                        }
                                }
                            }
                        )
                    }
                }
            }

            // TODO 진입 시, 그라데이션 높이 제대로 표기 안되는거 수정
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

            if (state.unSelectedGenre?.name.isNullOrBlank().not()) {

            }

            if (state.selectedGenre.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(bottom = 54.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    ShowpotColor.Gray700.copy(alpha = 0f),
                                    ShowpotColor.Gray700
                                ),
                            )
                        ).align(Alignment.BottomCenter),
                ) {
                    ShowPotMainButton(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        text = stringResource(R.string.subscribe)
                    ) {
                        scope.launch {
                            onSubscribeButtonClicked()
                            snackBarHostState.showSnackbar("구독 설정이 완료되었습니다")
                        }
                    }
                }
            }

        }
    }
}






