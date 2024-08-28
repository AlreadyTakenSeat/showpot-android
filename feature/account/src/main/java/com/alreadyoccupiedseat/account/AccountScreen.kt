package com.alreadyoccupiedseat.account

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.IconMenuWithCount
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_Regular
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Preview
@Composable
fun AccountScreenPreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    AccountScreen(navController)
}

@Composable
fun AccountScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<AccountViewModel>()
    val event = viewModel.event.collectAsState(AccountScreenEvent.Idle)
    val context = LocalContext.current
    when (event.value) {
        AccountScreenEvent.Idle -> {}
        AccountScreenEvent.AccountLogout -> {
            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            viewModel.clear()
        }
        AccountScreenEvent.Withdrawal -> {
            Toast.makeText(context, "회원 탈퇴가 요청되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    AccountScreenContent(
        onBackClicked = {
            navController.popBackStack()
        },
        onAccountLogOut = {
            viewModel.logout()
        },
        onWithdrawal = {
            viewModel.withdrawal()
        }

    )
}

@Composable
private fun AccountScreenContent(
    onBackClicked: () -> Unit,
    onAccountLogOut: () -> Unit,
    onWithdrawal: () -> Unit,
) {
    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            AccountTopBar(onBackClicked)
        },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 26.dp)
                    .padding(it),
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        ShowPotKoreanText_H2(
                            modifier = Modifier
                                .weight(1f),
                            text = "춤추는 고래", // TODO NickName
                            color = ShowpotColor.Gray100
                        )
                        ShowPotKoreanText_B1_Regular(
                            text = stringResource(R.string.kakao_login),
                            color = ShowpotColor.Gray100
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .padding(horizontal = 16.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(ShowpotColor.Gray500)
                    )
                }

                item {
                    IconMenuWithCount(
                        firstIcon = painterResource(id = R.drawable.ic_logout),
                        title = stringResource(R.string.logout),
                        tint = ShowpotColor.Gray100,
                        onClicked = {
                            onAccountLogOut()
                        }
                    )
                }

                item {
                    IconMenuWithCount(
                        firstIcon = painterResource(id = R.drawable.ic_withdrawal),
                        title = stringResource(R.string.withdrawal),
                        tint = ShowpotColor.Gray100,
                        onClicked = {
                            onWithdrawal()
                        }
                    )
                }

            }
        }
    )
}