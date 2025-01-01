package com.alreadyoccupiedseat.myalamrs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.common.infinitescroll.InfinityLazyColumn
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty
import com.alreadyoccupiedseat.designsystem.component.ShowPotAlert
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MyAlertsScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<MyAlertsViewModel>()
    val state by viewModel.collectAsState()
    MyAlertsContentScreen(
        state = state,
        onBackClicked = {
            navController.popBackStack()
        },
        loadMore = {
            viewModel.loadNextPage()
        }
    )
}

@Composable
private fun MyAlertsContentScreen(
    state: MyAlertsState,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    loadMore: () -> Unit = {},
) {
    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            MyAlertsTopBar { onBackClicked() }
        },
        content = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) TestProgress()
                if (state.alerts.isEmpty()) {
                    EmptyAlert()
                } else {
                    InfinityLazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize(),
                        loadMore = {
                            loadMore()
                        },
                        loadMoreLimitCount = 7
                    ) {
                        items(state.alerts) { alert ->
                            ShowPotAlert(
                                imageUrl = alert.showImageURL,
                                title = alert.title,
                                content = alert.message,
                                timeAt = alert.notifiedAt
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun TestProgress(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = ShowpotColor.MainOrange,
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun EmptyAlert() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        DefaultScreenWhenEmpty(
            text = stringResource(id = R.string.no_alerts),
            imageResId = R.drawable.img_empty_alert
        )
    }
}

