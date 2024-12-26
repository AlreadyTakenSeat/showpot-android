package com.lguplus.myalamrs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotAlert
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MyAlertsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel = hiltViewModel<MyAlertsViewModel>()
    val state by viewModel.collectAsState()
    MyAlertsContentScreen(
        navController = navController,
        state = state,
        onBackClicked = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun MyAlertsContentScreen(
    state: MyAlertsState,
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            MyAlertsTopBar { onBackClicked() }
        },
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(it),
            ) {
                repeat(10) {
                    item {
                        ShowPotAlert(
                            imageUrl = "https://images.pexels.com/photos/6865046/pexels-photo-6865046.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                            title = "Show Alert Title",
                            content = "Show Alert Content",
                            timeAt = "2024.12.25"
                        )
                    }
                }
            }
        }
    )
}