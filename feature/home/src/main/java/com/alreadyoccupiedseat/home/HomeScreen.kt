package com.alreadyoccupiedseat.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.core.extension.isScrollingUp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtist
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotMenu
import com.alreadyoccupiedseat.designsystem.component.ShowPotSearchBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent() {

    var isTopBarVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    val scrollState = rememberLazyListState()
    val firstVisibleItemIndex =
        remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value

    val context = LocalContext.current

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

    Box(
        modifier = Modifier
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
                        .height(133.dp)
                )
            }

            // item section
            item {
                ShowPotMenu(
                    text = stringResource(id = R.string.subscribe_artist),
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
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.padding(start = 16.dp, top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(count = 10) {
                        ShowPotArtist(
                            text = "High Flying Birds",
                            icon = painterResource(id = R.drawable.img_artist_default),
                        )
                    }
                }
            }
            items(count = 10) { index ->
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .background(if (index % 2 == 0) ShowpotColor.MainRed else ShowpotColor.MainGreen)
                        .fillMaxWidth()
                        .height(133.dp)
                )
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
                Image(
                    modifier = Modifier.padding(horizontal = 17.dp, vertical = 13.dp),
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = stringResource(com.alreadyoccupiedseat.home.R.string.showpot_logo_content_description)
                )

                ShowPotSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 14.dp),
                    hint = stringResource(com.alreadyoccupiedseat.home.R.string.search_shows_and_artists_hint),
                    enabled = false,
                    onClickedWhenDisEnabled = {
                        // Todo: navigate to the search page
                        Toast.makeText(context, "clicked when it's not enabled", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }
        }
    }
}

