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
import com.alreadyoccupiedseat.account.AccountScreen
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.ShowPotBottomNavigation
import com.alreadyoccupiedseat.entire_show.EntireShowScreen
import com.alreadyoccupiedseat.home.HomeScreen
import com.alreadyoccupiedseat.login.LoginScreen
import com.alreadyoccupiedseat.myalarm_setting.MyAlarmSettingScreen
import com.alreadyoccupiedseat.myfavorite_show.MyFavoriteShowScreen
import com.alreadyoccupiedseat.myfinished_show.MyFinishedShowScreen
import com.alreadyoccupiedseat.mypage.MyPageScreen
import com.alreadyoccupiedseat.notification.NotificationScreen
import com.alreadyoccupiedseat.search.SearchScreen
import com.alreadyoccupiedseat.settings.SettingsScreen
import com.alreadyoccupiedseat.show_detail.ShowDetailScreen
import com.alreadyoccupiedseat.showpot.Screen
import com.alreadyoccupiedseat.showpot.Screen.Companion.bottomNavigationItems
import com.alreadyoccupiedseat.subscribed_artist.SubscribedArtistScreen
import com.alreadyoccupiedseat.subscription_artist.SubscriptionArtistScreen
import com.alreadyoccupiedseat.subscription_genre.SubscriptionGenreScreen
import com.alreadyoccupiedseat.withdraw.WithDrawScreen

@Composable
fun AppScreen(
    onPrivacyPolicyClicked: () -> Unit = { },
    onTermsOfServiceClicked: () -> Unit = { },
    onNotificationSettingClicked: () -> Unit = { },
    versionName: String,
    onLabelButtonClicked: (String) -> Unit = { }
) {
    AppScreenContent(
        onPrivacyPolicyClicked = onPrivacyPolicyClicked,
        onTermsOfServiceClicked = onTermsOfServiceClicked,
        onNotificationSettingClicked = onNotificationSettingClicked,
        versionName = versionName,
        onLabelButtonClicked = onLabelButtonClicked
    )
}

@Composable
fun AppScreenContent(
    onPrivacyPolicyClicked: () -> Unit = { },
    onTermsOfServiceClicked: () -> Unit = { },
    onNotificationSettingClicked: () -> Unit = { },
    versionName: String,
    onLabelButtonClicked: (String) -> Unit = { }
) {
    val navController = rememberNavController()
    Scaffold(
        containerColor = ShowpotColor.Gray700,
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

            composable(Screen.Login.route) {
                LoginScreen(navController)
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    onSearchBarClicked = {
                        navController.navigate(Screen.Search.route)
                    },
                    onSubscriptionGenreClicked = {
                        navController.navigate(Screen.SubscriptionGenre.route)
                    },
                    onSubscribeArtistClicked = {
                        navController.navigate(Screen.SubscriptionArtist.route)
                    },
                    onEntireShowClicked = {
                        navController.navigate(Screen.EntireShowList.route)
                    },
                    onRecommendedShowClicked = {
                        navController.navigate(Screen.ShowDetail.route.replace("{showId}", it))
                    }
                )
            }

            composable(Screen.Notification.route) {
                NotificationScreen(
                    onShowClicked = {
                        navController.navigate(Screen.ShowDetail.route.replace("{showId}", it))
                    },
                    onLoginRequested = {
                        navController.navigate(Screen.Login.route)
                    },
                    onMyAlarmSettingClicked = {
                        navController.navigate(Screen.MyAlarmSetting.route)
                    },
                    onMyFavoriteShowsClicked = {
                        navController.navigate(Screen.MyFavoriteShows.route)
                    },
                    onMyFinishedShowClicked = {
                        navController.navigate(Screen.MyFinishedShow.route)
                    }
                )
            }

            composable(Screen.MyPage.route) {
                MyPageScreen(
                    onLoginClicked = {
                        navController.navigate(Screen.Login.route)
                    },
                    onSettingClicked = {
                        navController.navigate(Screen.Settings.route)
                    },
                    onMySubscribedArtistClicked = {
                        navController.navigate(Screen.SubscribedArtist.route)
                    },
                    onMySubscribedGenreClicked = {
                        navController.navigate(Screen.SubscriptionGenre.route)
                    }
                )
            }

            composable(Screen.Search.route) {
                SearchScreen(navController,
                    onShowClicked = { navController.navigate("showDetail/$it") },
                    onLoginRequested = {
                        navController.navigate(Screen.Login.route)
                    })
            }

            composable(Screen.SubscriptionGenre.route) {
                SubscriptionGenreScreen(
                    navController = navController,
                    onLoginRequested = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }

            composable(Screen.SubscriptionArtist.route) {
                SubscriptionArtistScreen(navController,
                    onLoginRequested = {
                        navController.navigate(Screen.Login.route)
                    },
                    onGoToSeeClicked = {
                        navController.navigate(Screen.SubscribedArtist.route)
                    })
            }

            composable(Screen.SubscribedArtist.route) {
                SubscribedArtistScreen(
                    navController = navController,
                    onGoToSubscriptionArtist = {
                        navController.navigate(Screen.SubscriptionArtist.route)
                    }
                )
            }

            composable(
                Screen.ShowDetail.route,
                Screen.ShowDetail.arguments
            ) { backStackEntry ->
                ShowDetailScreen(
                    navController,
                    backStackEntry.arguments?.getString("showId") ?: String.EMPTY
                ) {
                    onLabelButtonClicked(it)
                }
            }

            composable(Screen.MyAlarmSetting.route) {
                MyAlarmSettingScreen(
                    navController = navController,
                    onShowClicked = {
                        navController.navigate(Screen.ShowDetail.route.replace("{showId}", it))
                    },
                    onEntireShowClicked = {
                        navController.navigate(Screen.EntireShowList.route)
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    navController = navController,
                    onAccountClicked = {
                        navController.navigate(Screen.Account.route)
                    },
                    onPrivacyPolicyClicked = onPrivacyPolicyClicked,
                    onTermsOfServiceClicked = onTermsOfServiceClicked,
                    onNotificationSettingClicked = onNotificationSettingClicked,
                    versionName = versionName
                )
            }

            composable(Screen.Account.route) {
                AccountScreen(navController)
            }

            composable(Screen.MyFinishedShow.route) {
                MyFinishedShowScreen(navController)
            }

            composable(Screen.MyFavoriteShows.route) {
                MyFavoriteShowScreen(
                    navController = navController,
                    onShowClicked = {
                        navController.navigate(Screen.ShowDetail.route.replace("{showId}", it))
                    }
                )
            }

            composable(Screen.EntireShowList.route) {
                EntireShowScreen(
                    navController = navController,
                    onShowClicked = {
                        navController.navigate(Screen.ShowDetail.route.replace("{showId}", it))
                    }
                )
            }

            composable(Screen.WithDraw.route) {
                WithDrawScreen(navController)
            }

        }
    }
}