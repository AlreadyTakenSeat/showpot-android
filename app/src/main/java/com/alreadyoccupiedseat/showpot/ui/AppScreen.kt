package com.alreadyoccupiedseat.showpot.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.designsystem.component.ShowPotBottomNavigation
import com.alreadyoccupiedseat.home.HomeScreen
import com.alreadyoccupiedseat.mypage.MyPageScreen
import com.alreadyoccupiedseat.notification.NotificationScreen
import com.alreadyoccupiedseat.search.SearchScreen
import com.alreadyoccupiedseat.showpot.Screen
import com.alreadyoccupiedseat.showpot.Screen.Companion.bottomNavigationItems

@Composable
fun AppScreen() {

    AppScreenContent()
}

@Composable
fun AppScreenContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            AnimatedVisibility(
                visible = currentDestination?.route in bottomNavigationItems.map { it.route },
                enter = slideInVertically(initialOffsetY = {
                    it
                }),
                exit = slideOutVertically(targetOffsetY = {
                    it
                })
            ) {
                ShowPotBottomNavigation(
                    bottomNavigationItems = bottomNavigationItems,
                    currentDestination?.route ?: String.EMPTY
                ) {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            androidx.compose.ui.Modifier.padding(innerPadding),
        ) {

            composable(Screen.Home.route) {
                HomeScreen(navController) {
                    navController.navigate(Screen.Search.route)
                }
            }

            composable(Screen.Notification.route) {
                NotificationScreen(navController)
            }

            composable(Screen.MyPage.route) {
                MyPageScreen(navController)
            }

            composable(Screen.Search.route) {
                SearchScreen(navController)
            }
        }
    }
}