package com.alreadyoccupiedseat.notification

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import coil.compose.AsyncImage
import com.alreadyoccupiedseat.core.extension.calculateCurrentOffsetForPage
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H0
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H1
import com.alreadyoccupiedseat.designsystem.typo.english.ShowPotEnglishText_H5
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular
import com.alreadyoccupiedseat.model.temp.AlertReservedShow
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TicketSlidePagerForAlarmReservedShow(
    pagerState: PagerState,
    alarmedShows: List<AlertReservedShow>,
    onShowClicked: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 66.dp, vertical = 24.dp),
            beyondBoundsPageCount = 0,
            state = pagerState,
            modifier = Modifier
                .background(ShowpotColor.Gray700)
        ) { page ->

            val pageOffset = pagerState.calculateCurrentOffsetForPage(page = page)

            Box(
                modifier = Modifier
                    .clickable {
                        onShowClicked()
                    }
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
                        AsyncImage(
                            model = alarmedShows[page].imageURL,
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
                            text = alarmedShows[page].title,
                            color = ShowpotColor.Gray800,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        ShowPotKoreanText_B2_Regular(
                            modifier = Modifier
                                .padding(horizontal = 14.dp),
                            text = "${
                                alarmedShows[page].startAt.replace(
                                    "-",
                                    "."
                                )
                            } - ${alarmedShows[page].endAt.replace("-", ".")}",
                            color = ShowpotColor.Gray700,
                        )

                        ShowPotKoreanText_B2_Regular(
                            modifier = Modifier
                                .padding(horizontal = 14.dp),
                            text = alarmedShows[page].location,
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
                            text = formatDateTime(alarmedShows[page].cursorValue),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = ShowpotColor.Gray700,
                        )
                    }
                }

            }

        }
    }
}

internal fun formatDateTime(inputDateTimeStr: String): String {

    val inputDateTime = LocalDateTime.parse(inputDateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    val dayOfWeekAbbreviation = when (inputDateTime.dayOfWeek) {
        DayOfWeek.MONDAY -> "MON"
        DayOfWeek.TUESDAY -> "TUE"
        DayOfWeek.WEDNESDAY -> "WED"
        DayOfWeek.THURSDAY -> "THU"
        DayOfWeek.FRIDAY -> "FRI"
        DayOfWeek.SATURDAY -> "SAT"
        DayOfWeek.SUNDAY -> "SUN"
    }

    val outputFormatter = DateTimeFormatter.ofPattern("MM.dd")

    val formattedDate = inputDateTime.format(outputFormatter)
    val formattedTime = inputDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    return "$formattedDate($dayOfWeekAbbreviation) $formattedTime"
}

internal fun daysUntil(inputDateTimeStr: String): Long {
    // 현재 날짜와 시간
    val now = LocalDateTime.now()

    // 입력 문자열을 LocalDateTime 객체로 변환
    val inputDateTime = LocalDateTime.parse(inputDateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    // 현재 날짜와 입력된 날짜 사이의 차이를 계산
    val duration = Duration.between(now, inputDateTime)

    // 남은 일수 반환
    return duration.toDays()
}