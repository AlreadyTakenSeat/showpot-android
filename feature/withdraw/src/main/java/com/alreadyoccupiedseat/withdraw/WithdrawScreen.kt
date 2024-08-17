package com.alreadyoccupiedseat.withdraw

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotButton
import com.alreadyoccupiedseat.designsystem.component.textfield.UnderLinedTextField
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H1

@Composable
fun WithDrawScreen(
    navController: NavController
) {

    val viewModel = hiltViewModel<WithDrawViewModel>()
    val state = viewModel.state.collectAsState()

    WithDrawScreenContent(
        state = state.value,
        onBackClicked = {
            navController.popBackStack()
        },
        onTextChanged = { newString ->
            viewModel.updateInputText(newString)
        }
    )
}

@Composable
fun WithDrawScreenContent(
    state: WithDrawScreenState,
    onBackClicked: () -> Unit,
    onTextChanged: (String) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    painter = painterResource(id = com.alreadyoccupiedseat.designsystem.R.drawable.ic_arrow_36_left),
                    contentDescription = "back",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onBackClicked()
                    }
                )

                ShowPotKoreanText_H1(
                    text = "회원 탈퇴 ",
                    color = ShowpotColor.Gray300,
                )
            }
        },
        containerColor = ShowpotColor.Gray700
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(31.dp))

            UnderLinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 14.dp),
                hint = "구체적인 사유를 입력해주세요.",
                inputText = state.inputText,
                onTextChanged = { onTextChanged(it) }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 55.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                ShowPotButton(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    onClick = {

                    },
                    enabled = state.inputText.isNotEmpty(),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonColors(
                        containerColor = ShowpotColor.Gray500,
                        contentColor = Color.White,
                        disabledContainerColor = ShowpotColor.Gray600,
                        disabledContentColor = Color(0xFF454751),
                    ),
                ) {
                    ShowPotKoreanText_H1(
                        text = "제출하고 탈퇴하기",
                        color = if (state.inputText.isNotEmpty()) ShowpotColor.White else Color(
                            0xFF454751
                        ),
                    )
                }
            }
        }
    }
}

