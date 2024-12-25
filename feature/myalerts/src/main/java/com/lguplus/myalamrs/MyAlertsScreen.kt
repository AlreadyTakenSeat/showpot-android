package com.lguplus.myalamrs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alreadyoccupiedseat.designsystem.ShowpotColor

@Composable
fun MyAlertsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    MyAlertsContentScreen(
        navController = navController,
        onBackClicked = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun MyAlertsContentScreen(
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
                    .padding(top = 12.dp)
                    .padding(it),
            ) {

            }
        }
    )
}