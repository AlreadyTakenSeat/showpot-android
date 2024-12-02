package com.alreadyoccupiedseat.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alreadyoccupiedseat.core.extension.isScrollingUp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.RecommendedShow
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotMenu
import com.alreadyoccupiedseat.designsystem.component.ShowPotSearchBar
import com.alreadyoccupiedseat.designsystem.component.ShowPotTicket
import com.alreadyoccupiedseat.designsystem.component.artist.ShowPotArtist
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onSearchBarClicked: () -> Unit,
    onSubscriptionGenreClicked: () -> Unit,
    onSubscribeArtistClicked: () -> Unit,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit,
    onRecommendedShowClicked: (String) -> Unit,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.getUbSubscribedArtists()
        viewModel.getNickName()
    }

    HomeScreenContent(
        state = state.value,
        onSearchBarClicked = onSearchBarClicked,
        onSubscriptionGenreClicked = onSubscriptionGenreClicked,
        onSubscribeArtistClicked = onSubscribeArtistClicked,
        onShowClicked = onShowClicked,
        onEntireShowClicked = onEntireShowClicked,
        onRecommendedShowClicked = onRecommendedShowClicked
    )
}

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    onSearchBarClicked: () -> Unit,
    onSubscriptionGenreClicked: () -> Unit,
    onSubscribeArtistClicked: () -> Unit,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit,
    onRecommendedShowClicked: (String) -> Unit,
) {

    var isTopBarVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val firstVisibleItemIndex =
        remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value

    // 첫 번째 아이템이 보이면 무조건 상단바 노출
    if (firstVisibleItemIndex == 0) {
        isTopBarVisible = true
    } else {
        if (scrollState.isScrollingUp()) {
            if (isTopBarVisible.not()) {
                LaunchedEffect(key1 = true) {
                    scope.launch {
                        isTopBarVisible = true
                        delay(500L)
                    }
                }
            }
        } else {
            LaunchedEffect(key1 = true) {
                scope.launch {
                    isTopBarVisible = false
                    delay(500L)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ShowpotColor.Gray700),
            ) {
                Image(
                    modifier = Modifier.padding(horizontal = 17.dp, vertical = 13.dp),
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = stringResource(com.alreadyoccupiedseat.home.R.string.showpot_logo_content_description)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(ShowpotColor.Gray700),
        ) {

            LazyColumn(
                state = scrollState,
            ) {

                // area occupied as big as topbar section
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(66.dp)
                    )
                }

                // item section
                item {
                    ShowPotMenu(
                        onClick = {
                            onSubscriptionGenreClicked()
                        },
                        text = stringResource(id = R.string.subscribe_genre),
                        endIcon = painterResource(id = R.drawable.ic_arrow_36_right),
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val genreList = state.genreList
                        items(genreList.size) {
                            val resId = genreList[it].first
                            ShowPotGenre(icon = painterResource(id = resId))
                        }
                    }
                }

                item {
                    ShowPotMenu(
                        modifier = Modifier.padding(top = 36.dp),
                        text = stringResource(id = R.string.subscribe_artist),
                        endIcon = painterResource(id = R.drawable.ic_arrow_36_right),
                    ) {
                        onSubscribeArtistClicked()
                    }
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp, top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        state.unSubscribedArtists.forEach { curArtist ->
                            item {
                                ShowPotArtist(
                                    text = curArtist.name,
                                    imageUrl = curArtist.imageURL,
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(38.dp))
                }

                item {
                    ShowPotKoreanText_H1(
                        modifier = Modifier
                            .padding(vertical = 7.dp, horizontal = 16.dp),
                        text = stringResource(id = R.string.tickets_almost_sold_out),
                        color = ShowpotColor.Gray100
                    )
                }

                itemsIndexed(state.entireShowList.data) { index, show ->
                    val textColor = if (show.isOpen) {
                        ShowpotColor.MainBlue
                    } else {
                        ShowpotColor.MainYellow
                    }
                    val ticketingTime = show.ticketingAt
                    if (index != 0) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    ShowPotTicket(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        imageUrl = show.posterImageURL,
                        showTime = ticketingTime,
                        showTimeTextColor = textColor,
                        showName = show.title,
                        showLocation = show.location,
                        hasTicketingOpen = show.isOpen,
                        onClick = {
                            onShowClicked(show.id)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                onEntireShowClicked()
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(49.dp)
                            .background(ShowpotColor.Gray500)
                    ) {

                        ShowPotKoreanText_B1_SemiBold(
                            text = stringResource(id = R.string.view_all_show),
                            color = ShowpotColor.Gray100
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_24_right),
                            contentDescription = "arrow_right",
                            colorFilter = ColorFilter.tint(ShowpotColor.Gray300)
                        )

                    }
                }

                item {
                    ShowPotKoreanText_H1(
                        modifier = Modifier
                            .padding(top = 38.dp)
                            .padding(horizontal = 16.dp, vertical = 7.dp),
                        text = if (state.nickName.isNotEmpty()) "${state.nickName}을 위한 추천 공연" else "이달의 추천공연",
                        color = ShowpotColor.Gray100
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp, top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {

                        items(performances.size) { index ->
                            val performance = performances[index]
                            RecommendedShow(
                                imageUrl = performance.recommendedPerformanceThumbnailURL,
                                text = performance.recommendedPerformanceTitle,
                                onClick = {
                                    onRecommendedShowClicked(performance.showID)
                                }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }

            }

            AnimatedVisibility(
                visible = isTopBarVisible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ShowpotColor.Gray700),
                ) {
                    ShowPotSearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 14.dp),
                        hint = stringResource(com.alreadyoccupiedseat.home.R.string.search_shows_and_artists_hint),
                        enabled = false,
                        onClickedWhenDisEnabled = {
                            onSearchBarClicked()
                        }
                    )
                }
            }

        }
    }
}



