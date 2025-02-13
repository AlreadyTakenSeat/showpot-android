package com.alreadyoccupiedseat.model.comment

import androidx.annotation.Keep

@Keep
data class CommentResponse(
    val commentId: String,
    val content: String,
    val createdAt: String,
    val isBlocked: Boolean,
    val parentId: String,
    val profileURL: String,
    val userName: String
) {
    companion object {

        private var dummyCommentId = 1
        private var dummyParentId = 1
        val Dummy
            get() = CommentResponse(
            commentId = dummyCommentId++.toString(),
            content = listOf(
                "안녕하세요",
                "반가워요",
                "잘 지내세요",
                "댓글을 달아주세요",
            ).shuffled().first(),
            createdAt = "2021-09-01T00:00:00",
            isBlocked = false,
            parentId = dummyParentId++.toString(),
            profileURL = "https://picsum.photos/30",
            userName = "Hi"
        )
    }
}