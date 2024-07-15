package com.alreadyoccupiedseat.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alreadyoccupiedseat.core.extention.isScrollingUp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
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
                    contentDescription = "showpot logo"
                )

                ShowPotSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 14.dp),
                    hint = "관심있는 공연과 가수를 검색해보세요.",
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

