package com.alreadyoccupiedseat.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotButtonWithIcon
import com.alreadyoccupiedseat.designsystem.component.ShowPotTopBar
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreen(
    navController: NavController,
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<LoginViewModel>()
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect { event ->
        when (event) {
            is LoginScreenEvent.Idle -> {

            }

            is LoginScreenEvent.LoginRequested -> {

            }

            is LoginScreenEvent.LoginCompleted -> {
                navController.popBackStack()
            }

            is LoginScreenEvent.LoginError -> {
                Toast.makeText(context, event.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LoginContent(
        state = state,
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onKakaoLoginClicked = {
            viewModel.tryKakaoLogin(context)
        }
    )

}


@Composable
fun LoginContent(
    state: LoginScreenState,
    onBackButtonClicked: () -> Unit = {},
    onKakaoLoginClicked: () -> Unit,
) {

    Scaffold(
        topBar = {
            ShowPotTopBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBackButtonClicked()
                    }) {
                        Icon(
                            modifier = Modifier.padding(1.dp),
                            painter = painterResource(R.drawable.ic_arrow_36_left),
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = ShowpotColor.Gray700,
                contentColor = ShowpotColor.White
            )
        },
        content = {
            if (!state.isLoading) {
                Column(
                    modifier = Modifier
                        .background(ShowpotColor.Gray700)
                        .padding(it)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(49.dp))
                    Image(
                        modifier = Modifier
                            .padding(top = 49.dp)
                            .size(width = 135.58.dp, height = 54.dp),
                        painter = painterResource(R.drawable.img_logo),
                        contentDescription = "Logo"
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    ShowPotKoreanText_H2(
                        text = stringResource(R.string.login_screen_message),
                        color = ShowpotColor.White
                    )

                    Spacer(modifier = Modifier.size(72.dp))

                    Image(
                        modifier = Modifier.size(width = 178.35.dp, height = 155.dp),
                        painter = painterResource(R.drawable.img_login_logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Login Logo",
                    )

                    Spacer(modifier = Modifier.height(166.dp))

                    ShowPotButtonWithIcon(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = stringResource(R.string.button_login_with_kakao),
                        icon = painterResource(R.drawable.ic_kakao),
                        colors = ButtonColors(
                            containerColor = ShowpotColor.Kakao,
                            contentColor = Color.Black,
                            disabledContainerColor = ShowpotColor.Gray600,
                            disabledContentColor = ShowpotColor.Gray400,
                        ),
                        onClick = {
                            onKakaoLoginClicked()
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // TODO MVP 제외 -> 이후 구현
//                ShowPotButtonWithIcon(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            horizontal = 16.dp,
//                        ),
//                    text = stringResource(R.string.button_login_with_google),
//                    icon = painterResource(R.drawable.ic_google),
//                    colors = ButtonColors(
//                        containerColor = ShowpotColor.White,
//                        contentColor = Color.Black,
//                        disabledContainerColor = ShowpotColor.Gray600,
//                        disabledContentColor = ShowpotColor.Gray400,
//                    ),
//                    onClick = {
//                        /* 구글 로그인 */
//                    }
//                )
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(ShowpotColor.Gray700)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = ShowpotColor.MainOrange,
                        strokeWidth = 4.dp
                    )
                }
            }
        }
    )

}

