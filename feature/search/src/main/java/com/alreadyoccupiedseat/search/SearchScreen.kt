package com.alreadyoccupiedseat.search

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotSearchBar
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
) {

    val viewModel = hiltViewModel<SearchViewModel>()
    val state = viewModel.state.collectAsState()

    SearchScreenContent(
        state = state.value,
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
        }
    )
}

@Composable
fun SearchScreenContent(
    state: SearchScreenState,
    onBackButtonClicked: () -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onSearchIsDone: () -> Unit = {},
    onChipClicked: (String) -> Unit = {},
    onDeleteAllClicked: () -> Unit = {},
    onDeleteHistoryClicked: (String) -> Unit = {},
    onchangeArtistUnSubscriptionSheetVisibility: (Boolean) -> Unit = {},
    onChangeUnSubscribeTargetArtist: (String) -> Unit = {},
) {

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                focusManager.clearFocus()
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
                        .fillMaxWidth(),
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
                    onChipClicked = onChipClicked,
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
                ) {
                    onchangeArtistUnSubscriptionSheetVisibility(it)
                }
            }
        }
    }
}