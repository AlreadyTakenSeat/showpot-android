package com.alreadyoccupiedseat.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.core.extension.isScrollingUp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.RecommendedShow
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistByPainter
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotMenu
import com.alreadyoccupiedseat.designsystem.component.ShowPotSearchBar
import com.alreadyoccupiedseat.designsystem.component.ShowPotTicket
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    onSearchBarClicked: () -> Unit,
    onSubscriptionGenreClicked: () -> Unit,
    subscribeArtistClicked: () -> Unit
) {
    HomeScreenContent(
        onSearchBarClicked = onSearchBarClicked,
        onSubscriptionGenreClicked = onSubscriptionGenreClicked,
        subscribeArtistClicked = subscribeArtistClicked
    )
}

@Composable
fun HomeScreenContent(
    onSearchBarClicked: () -> Unit,
    onSubscriptionGenreClicked: () -> Unit,
    subscribeArtistClicked: () -> Unit
) {

    var isTopBarVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    val scrollState = rememberLazyListState()
    val firstVisibleItemIndex =
        remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value

    val viewModel = viewModel<HomeViewModel>()

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
                        items(viewModel.genreList) { (resId, _) ->
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
                        subscribeArtistClicked()
                    }
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp, top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(count = 10) {
                            ShowPotArtistByPainter(
                                text = "High Flying Birds",
                                icon = painterResource(id = R.drawable.img_artist_default),
                            )
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

                item {
                    ShowPotTicket(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .padding(horizontal = 16.dp),
                        imageUrl = "https://images.pexels.com/photos/6865046/pexels-photo-6865046.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                        showTime = "OPEN : 06.10(MON) AM 11:00",
                        showTimeTextColor = ShowpotColor.MainYellow,
                        showName = "Nothing But Thieves But Thieves ",
                        showLocation = "KBS 아레나홀",
                        onClick = {
                            Log.d("ShowPotTicket", "onClick")
                        }
                    )
                }

                item {
                    ShowPotTicket(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .padding(horizontal = 16.dp),
                        imageUrl = "https://images.pexels.com/photos/6865046/pexels-photo-6865046.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                        showTime = "OPEN : 06.10(MON) AM 11:00",
                        showTimeTextColor = ShowpotColor.MainBlue,
                        showName = "Nothing But Thieves But Thieves ",
                        showLocation = "KBS 아레나홀",
                        onClick = {
                            Log.d("ShowPotTicket", "onClick")
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
                        text = "춤추는 고래님을 위한 추천 공연", color = ShowpotColor.Gray100
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier.padding(start = 16.dp, top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        items(10) {
                            val (imageUrl, text) = if (it % 2 == 0) {
                                "https://img.hankyung.com/photo/202406/01.37069998.1.jpg" to "Nothing But Thieves But Thieves"
                            } else {
                                "https://thumb.mt.co.kr/06/2024/04/2024040913332068429_1.jpg/dims/optimize/" to "Christopher"
                            }
                            RecommendedShow(
                                imageUrl = imageUrl,
                                text = text,
                                onClick = {
                                    Log.d("RecommendedShow", "onClick")
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

