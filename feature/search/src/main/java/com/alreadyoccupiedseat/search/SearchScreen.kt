package com.alreadyoccupiedseat.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistSubscription
import com.alreadyoccupiedseat.designsystem.component.ShowPotSearchBar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
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
            navController.popBackStack()
        },
        onTextChanged = {
            viewModel.updateInputText(it)
        },
        onSearchIsDone = {
            viewModel.updateSearchHistories()
            viewModel.stateChangeToSearched()
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
        }
    )
}

@Composable
fun SearchScreenContent(
    state: SearchScreenState,
    onBackButtonClicked: () -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    onSearchIsDone: () -> Unit = {},
    onChipClicked: (String) -> Unit = {},
    onDeleteAllClicked: () -> Unit = {},
    onDeleteHistoryClicked: (String) -> Unit = {},
) {

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                Column {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        ShowPotKoreanText_H2(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = stringResource(R.string.artist), color = ShowpotColor.Gray100
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        item { Spacer(modifier = Modifier.width(4.dp))}
                        state.searchedArtists.forEach {
                            item {
                                ShowPotArtistSubscription(
                                    // TODO: Change to real artist image
                                    icon = painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.img_artist_default),
                                    text = it.name
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(top = 36.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        ShowPotKoreanText_H2(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = stringResource(R.string.show_information), color = ShowpotColor.Gray100
                        )
                    }
                }
            }
        }
    }
}