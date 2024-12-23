package com.alreadyoccupiedseat.designsystem.preview

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.artistByPainter.ShowPotArtistAlarmByPainter
import com.alreadyoccupiedseat.designsystem.component.artistByPainter.ShowPotArtistByPainter
import com.alreadyoccupiedseat.designsystem.component.artistByPainter.ShowPotArtistDeleteByPainter
import com.alreadyoccupiedseat.designsystem.component.artistByPainter.ShowPotArtistSubscriptionByPainter
import com.alreadyoccupiedseat.designsystem.component.ShowPotButtonWithIcon
import com.alreadyoccupiedseat.designsystem.component.ShowPotGenre
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.ShowPotMenu
import com.alreadyoccupiedseat.designsystem.component.ShowPotTicket
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H0
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun ComponentsPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            ShowPotKoreanText_H0(text = "버튼들")
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ShowPotKoreanText_H1(text = "CTA 버튼")
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            ShowPotMainButton(
                text = "enabled Main Button",
                enabled = true,
                onClicked = {}
            )
            Spacer(modifier = Modifier.height(18.dp))
        }

        item {
            ShowPotMainButton(
                text = "disabled Main Button",
                enabled = false,
                onClicked = {}
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
        
        item {
            ShowPotMainButton(
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 54.dp
                ),
                text = "enabled Main Button",
                enabled = true,
                onClicked = {})
        }
        
        item {
            ShowPotMainButton(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        bottom = 54.dp
                    ),
                text = "disabled Main Button With Padding",
                enabled = false,
                onClicked = {})
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        item {
            ShowPotButtonWithIcon(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(55.dp),
                text = stringResource(R.string.button_login_with_kakao),
                icon = painterResource(R.drawable.ic_kakao),
                colors = ButtonColors(
                    containerColor = ShowpotColor.Kakao,
                    contentColor = Color.Black,
                    disabledContainerColor = ShowpotColor.Gray600,
                    disabledContentColor = ShowpotColor.Gray400,
                ),
                onClick = {
                    /* 카카오 로그인 */
                }
            )
        }
        
        item {
            ShowPotButtonWithIcon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp,
                        horizontal = 20.dp,
                    )
                    .height(55.dp),
                text = stringResource(R.string.button_login_with_google),
                icon = painterResource(R.drawable.ic_google),
                colors = ButtonColors(
                    containerColor = ShowpotColor.White,
                    contentColor = Color.Black,
                    disabledContainerColor = ShowpotColor.Gray600,
                    disabledContentColor = ShowpotColor.Gray400,
                ),
                onClick = {
                    /* 구글 로그인 */
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ShowPotTicket(
                imageUrl = "https://img.hankyung.com/photo/202406/01.37069998.1.jpg",
                showTime = "OPEN : 06.10(MON) AM 11:00",
                showTimeTextColor = ShowpotColor.MainYellow,
                showName = "Nothing But Thieves But Thieves ",
                showLocation = "KBS 아레나홀",
                hasTicketingOpen = false,
                onClick = {
                    Log.d("ShowPotTicket", "onClick")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ShowPotTicket(
                imageUrl = "https://img.hankyung.com/photo/202406/01.37069998.1.jpg",
                showTime = "OPEN : 06.10(MON) AM 11:00",
                showTimeTextColor = ShowpotColor.MainBlue,
                showName = "Nothing But Thieves But Thieves ",
                showLocation = "KBS 아레나홀",
                hasTicketingOpen = true,
                onClick = {
                    Log.d("ShowPotTicket", "onClick")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ShowPotMenu(text = stringResource(id = R.string.subscribe_genre))
        }
        
        item {
            ShowPotMenu(
                text = stringResource(id = R.string.subscribe_genre),
                endIcon = painterResource(id = R.drawable.ic_arrow_36_right)
            ) {
                Log.d("menu", " onClick")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotMenu(
                text = stringResource(id = R.string.subscribe_genre),
                startIcon = painterResource(id = R.drawable.ic_alarm_24_default),
                endIcon = painterResource(id = R.drawable.ic_arrow_36_right)
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotArtistByPainter(
                icon = painterResource(id = R.drawable.img_artist_default),
                text = "High Flying Birds"
            )
        }

        item {
            var isSelected by remember { mutableStateOf(false) }
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotArtistAlarmByPainter(
                text = "High Flying Birds",
                isSelected = isSelected,
                onClick = {
                    isSelected = !isSelected
                },
                onIconClick = {
                    isSelected = !isSelected
                }
            )
        }

        item {
            var isSelected by remember { mutableStateOf(false) }
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotArtistSubscriptionByPainter(
                icon = painterResource(id = R.drawable.img_artist_default),
                text = "High Flying Birds",
                isSelected = isSelected,
                onClick = {
                    isSelected = !isSelected
                },
            )
        }

        item {
            var isSelected by remember { mutableStateOf(false) }
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotArtistDeleteByPainter(
                onIconClick = {
                    isSelected = !isSelected
                }
            )
        }

        val genreList = listOf(
            R.drawable.img_genre_rock to R.drawable.img_genre_selected_rock,
            R.drawable.img_genre_band to R.drawable.img_genre_selected_band,
            R.drawable.img_genre_edm to R.drawable.img_genre_selected_edm,
            R.drawable.img_genre_classic to R.drawable.img_genre_selected_classic,
            R.drawable.img_genre_hiphop to R.drawable.img_genre_selected_hiphop,
            R.drawable.img_genre_house to R.drawable.img_genre_selected_house,
            R.drawable.img_genre_opera to R.drawable.img_genre_selected_opera,
            R.drawable.img_genre_pop to R.drawable.img_genre_selected_pop,
            R.drawable.img_genre_rnb to R.drawable.img_genre_selected_rnb,
            R.drawable.img_genre_musical to R.drawable.img_genre_selected_musical,
            R.drawable.img_genre_metal to R.drawable.img_genre_selected_metal,
            R.drawable.img_genre_jpop to R.drawable.img_genre_selected_jpop,
        )

        item {
            ShowPotKoreanText_H1(text = "장르")
            LazyRow {
                items(genreList) { (resId, selectedResId) ->
                    ShowPotGenre(icon = painterResource(id = resId))
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            ShowPotKoreanText_H1(text = "Select 장르")
            LazyRow {
                items(genreList) { (resId, selectedResId) ->
                    var isSelected by rememberSaveable { mutableStateOf(false) }
                    ShowPotGenre(
                        enabled = true,
                        icon = painterResource(id = resId),
                        onSelectClicked = {
                            isSelected = !isSelected
                            Log.d("ShowPotBaseGenreView", "onSelectClick")
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ShowPotKoreanText_H1(text = "Delete 장르")
            LazyRow {
                items(genreList) { (resId, selectedResId) ->
                    ShowPotGenre(
                        icon = painterResource(id = resId),
                        isDeletable = true,
                        onDeleteClicked = {
                            Log.d("ShowPotBaseGenreView", "onDeleteClicked")
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}
