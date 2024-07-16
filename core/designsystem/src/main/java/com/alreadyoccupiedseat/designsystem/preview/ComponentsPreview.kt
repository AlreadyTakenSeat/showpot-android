package com.alreadyoccupiedseat.designsystem.preview

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtist
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistAlarm
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistDelete
import com.alreadyoccupiedseat.designsystem.component.ShowPotArtistSubscription
import com.alreadyoccupiedseat.designsystem.component.ShowPotButtonWithIcon
import com.alreadyoccupiedseat.designsystem.component.ShowPotMainButton
import com.alreadyoccupiedseat.designsystem.component.ShowPotMenu
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
            ShowPotMenu(text = "장르 구독하기")
        }
        item {
            ShowPotMenu(
                text = "장르 구독하기",
                endIcon = painterResource(id = R.drawable.ic_arrow_36_right)
            ) {
                Log.d("menu", " onClick")
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotMenu(
                text = "장르 구독하기",
                startIcon = painterResource(id = R.drawable.ic_alarm_24_default),
                endIcon = painterResource(id = R.drawable.ic_arrow_36_right)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotArtist(
                icon = painterResource(id = R.drawable.img_artist_default),
                text = "High Flying Birds"
            )
        }
        item {
            var isSelected by remember { mutableStateOf(false) }
            Spacer(modifier = Modifier.height(16.dp))
            ShowPotArtistAlarm(
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
            ShowPotArtistSubscription(
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
            ShowPotArtistDelete(
                onIconClick = {
                    isSelected = !isSelected
                }
            )
        }
    }
}
