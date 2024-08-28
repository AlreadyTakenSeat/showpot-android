package com.alreadyoccupiedseat.entire_show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotTicket

@Preview
@Composable
fun EntireShowScreenPreview() {
    val navController = rememberNavController()
    EntireShowScreen(navController)
}

@Composable
fun EntireShowScreen(
    navController: NavController,
) {

    val viewModel = hiltViewModel<EntireShowViewModel>()
    val state = viewModel.state.collectAsState()
    EntireShowScreenContent(
        state = state.value,
        onBackClicked = {
            navController.popBackStack()
        },
        onShowClicked = {
            // TODO 공연 상세 화면으로 이동
        }
    )

}

@Composable
private fun EntireShowScreenContent(
    state: EntireShowState,
    onBackClicked: () -> Unit,
    onShowClicked: (String) -> Unit,
) {

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            EntireShowTopBar(onBackClicked = {
                onBackClicked()
            })
        },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(it),
            ) {
                if (state.entireShowList.data.isEmpty()) {
                    // TODO 전체 공연 데이터 없을 경우
                } else {
                    itemsIndexed(state.entireShowList.data) { index, show ->
                        // TODO index로 수정 필요
                        val textColor = if (show.isOpen) {
                            ShowpotColor.MainBlue
                        } else {
                            ShowpotColor.MainYellow
                        }
                        val ticketingTime = show.ticketingAt

                        ShowPotTicket(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            imageUrl = show.posterImageURL,
                            showTime = ticketingTime,
                            showTimeTextColor = textColor,
                            showName = show.title,
                            showLocation = show.location,
                            hasTicketingOpen = show.isOpen,
                            onClick = {
                                onShowClicked(show.id)
                            }
                        )

                    }
                }
            }
        }
    )
}