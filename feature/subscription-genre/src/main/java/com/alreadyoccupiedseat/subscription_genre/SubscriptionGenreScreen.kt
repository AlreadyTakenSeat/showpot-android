package com.alreadyoccupiedseat.subscription_genre

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotTopBar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun SubscriptionGenreScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    SubscriptionGenreScreenContent(modifier = modifier, navController = navController)
}

@Composable
fun SubscriptionGenreScreenContent(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel = viewModel<SubscriptionGenreViewModel>()

    Scaffold(
        topBar = {
            ShowPotTopBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            modifier = Modifier.padding(1.dp),
                            painter = painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.ic_arrow_36_left),
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = ShowpotColor.Gray700,
                contentColor = ShowpotColor.White
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .background(ShowpotColor.Gray700)
                    .padding(it)
                    .fillMaxSize(),
            ) {

                ShowPotKoreanText_H2(
                    modifier = Modifier.padding(start = 16.dp, top = 5.dp),
                    text = stringResource(id = R.string.subscribe_genre_notification),
                    color = ShowpotColor.Gray300,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                ) {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
//                            .background(ShowpotColor.MainRed)
                    ) {

                        items(viewModel.tempGenreList.chunked(2)) { pair ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                pair.forEachIndexed { index, (resId, selectedResId) ->
                                    if (index % 2 != 0 ) {
                                        Spacer(modifier = Modifier.padding(end = 7.dp))
                                    } else {
                                        Spacer(modifier = Modifier.padding(start = 7.dp))
                                    }
                                    var isSelected by rememberSaveable { mutableStateOf(false) }
                                    ShowPotGenre(
                                        enabled = true,
                                        icon = painterResource(id = resId),
                                        selectedIcon = painterResource(id = selectedResId),
                                        isSelected = isSelected,
                                        onSelectClicked = {
                                            isSelected = !isSelected
                                            Log.d("ShowPotBaseGenreView", "onSelectClick")
                                        }
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    )
}
