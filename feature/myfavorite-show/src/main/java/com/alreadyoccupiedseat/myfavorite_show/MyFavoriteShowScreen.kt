package com.alreadyoccupiedseat.myfavorite_show

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alreadyoccupiedseat.common.infinitescroll.InfinityLazyColumn
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty
import com.alreadyoccupiedseat.designsystem.component.ShowInfo
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular
import org.orbitmvi.orbit.compose.collectAsState

@Preview
@Composable
fun MyFavoriteShowScreenPreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    MyFavoriteShowScreen(
        navController = navController,
        onShowClicked = {},
        onEntireShowClicked = {}
    )
}

@Composable
fun MyFavoriteShowScreen(
    navController: NavController,
    onShowClicked: (String) -> Unit,
    onEntireShowClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<MyFavoriteShowViewModel>()
    val state = viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getInterestedShow()
    }

    MyFavoriteShowScreenContent(
        state = state.value,
        modifier = Modifier,
        onLoadMore = {
            viewModel.loadMore()
        },
        onBackClicked = {
            navController.popBackStack()
        },
        onShowClicked = {
            onShowClicked(it)
        },
        onEntireShowClicked = {
            onEntireShowClicked()
        },
        onDeletedMyFavoriteShow = {
            viewModel.deleteMyFavoriteShow(it)
        }
    )
}

typealias showId = String

@Composable
private fun MyFavoriteShowScreenContent(
    state: MyFavoriteShowState,
    modifier: Modifier,
    onLoadMore: () -> Unit,
    onBackClicked: () -> Unit,
    onShowClicked: (String) -> Unit,
    onDeletedMyFavoriteShow: (showId) -> Unit,
    onEntireShowClicked: () -> Unit,
) {

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            MyFavoriteShowTopBar(onBackClicked = onBackClicked)
        },
        content = {
            InfinityLazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(top = 12.dp)
                    .padding(it),
                loadMore = {
                    onLoadMore()
                },
                loadMoreLimitCount = 10,
            ) {

                if (state.interestedShowList.isEmpty()) {
                    item {
                        MyFavoriteEmpty(
                            onEntireShowClicked = {
                                onEntireShowClicked()
                            }
                        )
                    }
                } else {
                    itemsIndexed(state.interestedShowList) { index, show ->
                        ShowInfo(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    onShowClicked(show.id)
                                },
                            imageUrl = show.posterImageURL,
                            showTitle = show.title,
                            dateInfo = show.startAt.replace("-", "."),
                            locationInfo = show.location,
                            icon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(2.dp))
                                        .clickable {
                                            onDeletedMyFavoriteShow(show.id)
                                        }
                                        .background(ShowpotColor.Gray500)
                                        .padding(vertical = 5.dp)
                                        .padding(start = 5.dp, end = 10.dp)

                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        painter = painterResource(R.drawable.ic_delete_24),
                                        contentDescription = null,
                                        tint = ShowpotColor.Gray300
                                    )

                                    ShowPotKoreanText_B2_Regular(
                                        modifier = Modifier,
                                        text = stringResource(R.string.delete),
                                        color = ShowpotColor.White,
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )

}

@Composable
fun MyFavoriteEmpty(
    onEntireShowClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxSize()
    ) {

        DefaultScreenWhenEmpty(
            imageResId = R.drawable.img_empty_alarm,
            text = stringResource(id = R.string.no_favorite_show)
        )

        Spacer(modifier = Modifier.height(96.dp))

        ShowPotSubButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.action_show_info),
            onClicked = {
                onEntireShowClicked()
            }
        )
    }
}