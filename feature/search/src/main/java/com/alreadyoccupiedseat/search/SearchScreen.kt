package com.alreadyoccupiedseat.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotSearchBar
import com.alreadyoccupiedseat.designsystem.component.snackbar.CheckIconSnackbar
import com.alreadyoccupiedseat.designsystem.component.snackbar.ShowPotSnackbar
import com.alreadyoccupiedseat.model.SubscribedArtist
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    onShowClicked: (String) -> Unit = {}
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.event.collect {
            when (it) {
                is SearchScreenEvent.Idle -> {

                }

                is SearchScreenEvent.SubscribeArtistSuccess -> {
                    snackbarHostState.showSnackbar("구독 설정이 완료되었습니다")
                }

                is SearchScreenEvent.UnSubscribeArtistSuccess -> {
                    snackbarHostState.showSnackbar("구독 해제가 완료되었습니다")
                }
            }
        }
    }
    SearchScreenContent(
        state = state.value,
        snackbarHostState = snackbarHostState,
        onBackButtonClicked = {
            if (state.value.isSearchedScreen) {
                viewModel.stateChangeToNotSearched()
            } else {
                navController.popBackStack()
            }
        },
        onTextChanged = {
            viewModel.updateInputText(it)
        },
        onCancelClicked = {
            viewModel.updateInputText(String.EMPTY)
            viewModel.stateChangeToNotSearched()
        },
        onSearchIsDone = {
            viewModel.updateSearchHistories()
            viewModel.searchArtistsAndShows()
        },
        onChipClicked = {
            // Should i group them into one function?
            viewModel.updateInputText(it)
            viewModel.searchArtistsAndShows()
        },
        onDeleteAllClicked = {
            viewModel.deleteAllSearchHistory()
        },
        onDeleteHistoryClicked = {
            viewModel.deleteSearchHistory(it)
        },
        onchangeArtistUnSubscriptionSheetVisibility = {
            viewModel.changeArtistUnSubscriptionSheetVisibility(it)
        },
        onChangeUnSubscribeTargetArtist = {
            viewModel.changeUnSubscribeTargetArtist(it)
        },
        onShowClicked = {
            onShowClicked(it)
        },
        onRequestSubscribeArtist = {
            viewModel.subscribeArtist(it)
        },
        onRequestUnSubscribeArtist = {
            viewModel.unSubscribeArtist()
        }
    )
}

@Composable
fun SearchScreenContent(
    state: SearchScreenState,
    snackbarHostState: SnackbarHostState,
    onBackButtonClicked: () -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onSearchIsDone: () -> Unit = {},
    onChipClicked: (String) -> Unit = {},
    onDeleteAllClicked: () -> Unit = {},
    onDeleteHistoryClicked: (String) -> Unit = {},
    onchangeArtistUnSubscriptionSheetVisibility: (Boolean) -> Unit = {},
    onChangeUnSubscribeTargetArtist: (SubscribedArtist) -> Unit = {},
    onShowClicked: (String) -> Unit = {},
    onRequestSubscribeArtist: (String) -> Unit = {},
    onRequestUnSubscribeArtist: () -> Unit = {},
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(focusRequester) {
        if (state.isSearchedScreen.not()) {
            focusRequester.requestFocus()
        }
    }

    BackHandler {
        onBackButtonClicked()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                focusManager.clearFocus()
            },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            ) { snackbarData ->
                ShowPotSnackbar(
                    painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_check_36),
                    mainText = snackbarData.visuals.message,
                    onIconClicked = {
                        // onIconClicked()
                    },
                ) {
                    // onActionClicked()
                }
            }
        },
        containerColor = ShowpotColor.Gray700,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp, top = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        onBackButtonClicked()
                    },
                    painter = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_arrow_36_left),
                    contentDescription = null,
                    tint = ShowpotColor.Gray300,
                )

                Spacer(modifier = Modifier.width(8.dp))

                ShowPotSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    hint = stringResource(R.string.search_shows_and_artists_hint),
                    inputText = state.inputText,
                    onTextChanged = {
                        onTextChanged(it)
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            scope.launch {
                                focusManager.clearFocus()
                                onSearchIsDone()
                            }
                        }
                    ),
                    onCancelClicked = {
                        focusManager.clearFocus()
                        onCancelClicked()
                    }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 12.dp)
        ) {

            if (state.isSearchedScreen.not()) {
                RecentSearchHistorySection(
                    searchHistories = state.searchHistory,
                    onDeleteAllClicked = onDeleteAllClicked,
                    onChipClicked = {
                        focusManager.clearFocus()
                        onChipClicked(it)
                    },
                    onDeleteHistoryClicked = onDeleteHistoryClicked
                )
            } else {
                SearchedSection(
                    isArtistUnSubscriptionSheetVisible = state.isArtistUnSubscriptionSheetVisible,
                    searchedArtists = state.searchedArtists,
                    searchedShows = state.searchedShows,
                    unSubscribeTargetArtist = state.unSubscribeTargetArtist,
                    onUnSubscribeTargetArtistChanged = {
                        onChangeUnSubscribeTargetArtist(it)
                    },
                    onShowClicked = onShowClicked,
                    onArtistUnSubscriptionSheetVisibilityChanged = {
                        onchangeArtistUnSubscriptionSheetVisibility(it)
                    },
                    onSubscribeArtist = {
                        onRequestSubscribeArtist(it)
                    },
                    onUnSubscribeArtist = {
                        onRequestUnSubscribeArtist()
                    }
                )
            }
        }
    }
}