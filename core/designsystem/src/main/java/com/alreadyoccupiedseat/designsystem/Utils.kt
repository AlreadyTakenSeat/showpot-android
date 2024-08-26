package com.alreadyoccupiedseat.designsystem

import androidx.compose.ui.graphics.Color

fun String.getTicketSiteButtonColor(): Color {
    return when (this) {
        "YES24" -> ShowpotColor.YES24
        "인터파크" -> ShowpotColor.Interpark
        "멜론티켓" -> ShowpotColor.Melon
        else -> ShowpotColor.MainOrange
    }
}