package com.alreadyoccupiedseat.data.comment

import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.comment.CommentResponse

interface CommentRepository {

    suspend fun postComment(
        refId: String,
        commentType: String,
        content: String,
        parentId: String
    ): Result<Unit>

    suspend fun reportOrBlockComment(
        commentId: String,
        directInput: String,
        reportType: String
    ): Result<Unit>

    suspend fun deleteComment(
        commentId: String
    ): Result<Unit>

    suspend fun getComments(
        refId: String,
        type: String,
        isInverted: Boolean,
        cursorId: String? = null,
        cursorValue: String? = null,
        size: Int
    ): Result<PagingData<CommentResponse>>

}