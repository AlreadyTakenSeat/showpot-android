package com.alreadyoccupiedseat.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotButtonWithIcon
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel<LoginViewModel>()
    val event = viewModel.event.collectAsState()

    LoginContent(
        event = event.value,
        onLoginCompleted = viewModel::loginCompleted,
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    event: LoginScreenEvent,
    onLoginCompleted: () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_36_left),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ShowpotColor.Gray700,
                    navigationIconContentColor = ShowpotColor.White,
                )
            )
        },
        content = {
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
                        /* 카카오 로그인 */
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ShowPotButtonWithIcon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                        ),
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
        }
    )

    when (event) {
        is LoginScreenEvent.Idle -> {

        }

        is LoginScreenEvent.LoginRequested -> {

        }

        is LoginScreenEvent.LoginCompleted -> {
            onLoginCompleted()
        }

        is LoginScreenEvent.LoginError -> {

        }
    }

}

@Composable
fun CustomTopAppBar(
    navigationIcon: @Composable (() -> Unit),
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .padding(0.dp)
                    .height(44.dp)
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                navigationIcon()
            }
        }
    }
}
