package com.alreadyoccupiedseat.model.show

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
    }
}