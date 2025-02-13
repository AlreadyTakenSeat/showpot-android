package com.alreadyoccupiedseat.model.comment

import androidx.annotation.Keep

@Keep
data class PostCommentRequest(
    val commentType: String,
    val content: String,
    val parentId: String,
    val refId: String
)