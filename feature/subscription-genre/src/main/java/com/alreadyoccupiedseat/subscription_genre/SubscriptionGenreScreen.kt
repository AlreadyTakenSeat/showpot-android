package com.alreadyoccupiedseat.subscription_genre

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotTopBar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1
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
    val genreList = remember { viewModel.tempGenreList }

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
                title = {
                    ShowPotKoreanText_H1(
                        text = stringResource(id = com.alreadyoccupiedseat.designsystem.R.string.subscribe_genre),
                        color = ShowpotColor.Gray100,
                        modifier = Modifier.padding(start = 4.dp)
                    )
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

                Spacer(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .padding(horizontal = 48.dp)
                        .fillMaxSize()
                ) {

                    LazyVerticalStaggeredGrid(
                        state = rememberLazyStaggeredGridState(),
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 40.dp,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(genreList.size) { index ->

                            val isEvenIndex = index % 2 == 0
                            val (resId, selectedResId) = genreList[index]
                            var isSelected by rememberSaveable { mutableStateOf(false) }
                            val align = if (isEvenIndex) Alignment.TopEnd else Alignment.TopStart

                            Box(
                                modifier = Modifier
                                    .padding(top = if (index == 1) 90.dp else 0.dp)
                            ) {
                                ShowPotGenre(
                                    modifier = Modifier.align(align),
                                    enabled = true,
                                    icon = painterResource(id = resId),
                                    selectedIcon = painterResource(id = selectedResId),
                                    isSelected = isSelected,
                                    onSelectClicked = {
                                        isSelected = !isSelected
                                    }
                                )
                            }
                        }
                    }
                    // gradient
                    Spacer(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        ShowpotColor.Gray700.copy(alpha = 0f),
                                        ShowpotColor.Gray700
                                    ),
                                )
                            ).align(Alignment.BottomCenter)
                    )

                }
            }
        }
    )
}
