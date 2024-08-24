package com.alreadyoccupiedseat.subscribed_artist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.DefaultScreenWhenEmpty
import com.alreadyoccupiedseat.designsystem.component.artist.ShowPotArtistDelete
import com.alreadyoccupiedseat.designsystem.component.button.ShowPotSubButton

@Composable
fun SubscribedArtistScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<SubscribedArtistViewModel>()
    val state = viewModel.state.collectAsState()
    SubscribedArtistContent(
        state = state.value,
        modifier = Modifier,
        onBackClicked = {
            navController.popBackStack()
        },
        onDeletedSubscribedArtist = {
            viewModel.deleteSubscribedArtist(it)
        }
    )
}

typealias artistId = String

@Composable
private fun SubscribedArtistContent(
    state: SubscribedArtistState,
    modifier: Modifier,
    onBackClicked: () -> Unit,
    onDeletedSubscribedArtist: (artistId) -> Unit,
) {

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            SubscribedArtistTopBar(onBackClicked = onBackClicked)
        },
        content = {

            Column(
                modifier = Modifier
                    .padding(it),
            ) {
                if (state.subscribedArtists.isEmpty()) {
                    SubscribedArtistEmpty()
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .fillMaxSize()
                        .padding(top = 24.dp),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // TODO: Real Data
                    items(state.subscribedArtists) { artist ->
                        ShowPotArtistDelete(
                            imageUrl = artist.imageURL,
                            onIconClick = {
                                onDeletedSubscribedArtist(artist.id)
                            }
                        )
                    }
                }
            }
        }
    )

}

@Composable
private fun SubscribedArtistEmpty() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxWidth()
    ) {

        DefaultScreenWhenEmpty(
            imageResId = R.drawable.img_empty_subscription,
            text = stringResource(id = R.string.no_subscribed_artists)
        )

        Spacer(modifier = Modifier.height(96.dp))

        ShowPotSubButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.action_subscribe_artist),
            onClicked = {
                // TODO 아티스트 구독 화면 이동
            }
        )
    }
}