package com.alreadyoccupiedseat.show_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.button.IconButtonWithShowPotMainButton
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun ShowDetailScreen(
    navController: NavController,
) {
    ShowDetailScreenContent()
}

@Composable
fun ShowDetailScreenContent() {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = ShowpotColor.Gray700,
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = androidx.compose.ui.Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Show Image
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(524.dp),
                    // TODO: Real Image URL
                    model = "https://cdn-p.smehost.net/sites/5cfaf3980b294dd89a79248f35560b2f/wp-content/uploads/2024/02/NBT-NA-Poster-1x1-1.png",
                    contentDescription = "show image",
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                )
            }

            // Topbar Section
            Column(
                modifier = Modifier.fillMaxWidth()
                    .height(153.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                ShowpotColor.Gray800.copy(alpha = 0.5f),
                                ShowpotColor.Gray800.copy(alpha = 0f),
                            ),
                        )
                    )
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp, bottom = 4.dp),
                        painter = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_arrow_36_left),
                        contentDescription = "backButton",
                        tint = Color.White,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    ShowPotKoreanText_H1(
                        text = stringResource(R.string.show_information),
                        color = Color.White,
                    )
                }
            }

            // CTA Button Container
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                IconButtonWithShowPotMainButton(
                    painterResource(com.alreadyoccupiedseat.designsystem.R.drawable.ic_heart_36_off),
                    stringResource(R.string.set_notification),
                    onIconButtonClicked = {

                    },
                    onMainButtonClicked = {

                    }
                )

            }
        }
    }
}