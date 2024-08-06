package com.alreadyoccupiedseat.designsystem.component.ticketSlider

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import com.alreadyoccupiedseat.core.extension.calculateCurrentOffsetForPage
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H0
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H1
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H5
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular
import kotlin.math.absoluteValue

// TODO: Need Real Data
@SuppressLint("RestrictedApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TicketSlidePager(
    pagerState: PagerState,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 66.dp, vertical = 24.dp),
            beyondBoundsPageCount = 2,
            state = pagerState,
            modifier = Modifier
                .background(ShowpotColor.Gray700)
        ) { page ->

            val pageOffset = pagerState.calculateCurrentOffsetForPage(page = page)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.75f)
                    .graphicsLayer {
                        val scale = lerp(1f, 0.8f, pageOffset.absoluteValue)
                        scaleX = scale
                        scaleY = scale
                        alpha = 1 - pageOffset.absoluteValue / 2
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(258.dp)
                        .height(368.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            when (page % 4) {
                                1 -> com.alreadyoccupiedseat.designsystem.R.drawable.img_ticket_green
                                2 -> com.alreadyoccupiedseat.designsystem.R.drawable.img_ticket_blue
                                3 -> com.alreadyoccupiedseat.designsystem.R.drawable.img_ticket_yellow
                                else -> com.alreadyoccupiedseat.designsystem.R.drawable.img_ticket_orange
                            }
                        ),
                        contentDescription = "Ticket Background",
                        modifier =
                        Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(
                                com.alreadyoccupiedseat.designsystem.R.drawable.img_ticket_dummy
                            ),
                            contentDescription = "Check",
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ShowPotEnglishText_H0(
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .fillMaxWidth(),
                            text = "OPN(Oneohtrix Point Never)",
                            color = ShowpotColor.Gray800,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        ShowPotKoreanText_B2_Regular(
                            modifier = Modifier
                                .padding(horizontal = 14.dp),
                            text = "2024.12.4 - 2024.12.5",
                            color = ShowpotColor.Gray700,
                        )

                        ShowPotKoreanText_B2_Regular(
                            modifier = Modifier
                                .padding(horizontal = 14.dp),
                            text = "KBS 아레나 홀",
                            color = ShowpotColor.Gray700,
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        ShowPotEnglishText_H5(
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .fillMaxWidth(),
                            text = "TICKET OPEN",
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = ShowpotColor.Gray700,
                        )
                        ShowPotEnglishText_H1(
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .fillMaxWidth(),
                            text = "06.10(MON) AM 11:00",
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = ShowpotColor.Gray700,
                        )
                    }
                }

            }

        }
    }
}