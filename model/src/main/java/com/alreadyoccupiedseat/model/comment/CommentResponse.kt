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
)