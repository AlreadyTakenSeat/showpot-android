package com.alreadyoccupiedseat.showpot

import androidx.annotation.DrawableRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.alreadyoccupiedseat.Screens
import com.alreadyoccupiedseat.designsystem.R

sealed class Screen(
    route: String,
    title: String,
    @DrawableRes unSelectedIcon: Int? = null,
    @DrawableRes selectedIcon: Int? = null,
    val arguments: List<NamedNavArgument> = emptyList(),
): Screens(
    route = route,
    title = title,
    unSelectedIcon = unSelectedIcon,
    selectedIcon = selectedIcon,
) {

    data object Login: Screen(
        route = "login",
        title = "로그인"
    )

    data object Home : Screen(
        route = "home",
        title = "홈",
        unSelectedIcon = R.drawable.ic_home_24,
        selectedIcon = R.drawable.ic_home_24_filled,
    )

    data object Notification: Screen(
        route = "notification",
        title = "내 공연",
        unSelectedIcon = R.drawable.ic_my_show_24,
        selectedIcon = R.drawable.ic_my_show_24_filled,
    )

    data object MyPage: Screen(
        route = "myPage",
        title = "마이",
        unSelectedIcon = R.drawable.ic_my_24,
        selectedIcon = R.drawable.ic_my_24_filled,
    )

    data object Search: Screen(
        route = "search",
        title = "검색"
    )

    data object SubscriptionGenre: Screen(
        route = "genre",
        title = "장르 구독"
    )

    data object SubscriptionArtist: Screen(
        route = "subscriptionArtist",
        title = "아티스트 구독"
    )

    data object SubscribedArtist: Screen(
        route = "subscribedArtist",
        title = "구독한 아티스트"
    )

    data object ShowDetail: Screen(
        route = "showDetail/{showId}",
        title = "공연정보",
        arguments = listOf(
            navArgument("showId") { type = NavType.StringType },
        ),
    )

    data object MyAlarmSetting: Screen(
        route = "myAlarmSetting",
        title = "알림 설정"
    )

    data object Settings: Screen(
        route = "settings",
        title = "설정"
    )

    data object MyFinishedShow: Screen(
        route = "myFinishedShow",
        title = "완료된 공연"
    )

    data object MyFavoriteShows: Screen(
        route = "myFavoriteShows",
        title = "관심 공연"
    )

    data object EntireShowList: Screen(
        route = "entireShowList",
        title = "전체 공연 목록"
    )

    data object Account: Screen(
        route = "account",
        title = "계정"
    )

    data object WithDraw: Screen(
        route = "withDraw",
        title = "회원 탈퇴"
    )

    companion object {
        val bottomNavigationItems = listOf(Home, Notification, MyPage)
    }
}