package com.alreadyoccupiedseat.model.show

import androidx.annotation.Keep

@Keep
data class Shows(
    val data: List<Data>,
    val hasNext: Boolean,
    val size: Int
) {
    companion object {
        const val RECENT = "RECENT"
        const val POPULAR = "POPULAR"

        // 공연 알림 목록 조회 타입
        const val CONTINUE = "CONTINUED"
        const val TERMINATE = "TERMINATE"

        // 티켓팅 타입
        const val NORMAL = "NORMAL"
    }
}