package com.alreadyoccupiedseat.model.show

import androidx.annotation.Keep

@Keep
enum class ShowType(val text: String) {
    RECENT("RECENT"),
    POPULAR("POPULAR"),

    // 공연 알림 목록 조회 타입
    CONTINUE("CONTINUED"),
    TERMINATE("TERMINATE"),

    // 티켓팅 타입
    NORMAL("NORMAL"),
}