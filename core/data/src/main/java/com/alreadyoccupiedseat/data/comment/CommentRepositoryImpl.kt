package com.alreadyoccupiedseat.data.comment

import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.comment.CommentResponse
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentDataSource: CommentDataSource
): CommentRepository {
    override suspend fun postComment(
        refId: String?,
        commentType: String,
        content: String,
        parentId: String
    ): Result<Unit> {
        return commentDataSource.postComment(refId, commentType, content, parentId)
    }

    override suspend fun reportOrBlockComment(
        commentId: String,
        directInput: String,
        reportType: String
    ): Result<Unit> {
        return commentDataSource.reportOrBlockComment(commentId, directInput, reportType)
    }

    override suspend fun deleteComment(commentId: String): Result<Unit> {
        return commentDataSource.deleteComment(commentId)
    }

    override suspend fun getComments(
        refId: String,
        type: String,
        isInverted: Boolean,
        cursorId: String?,
        cursorValue: String?,
        size: Int
    ): Result<PagingData<CommentResponse>> {
        return commentDataSource.getComments(refId, type, isInverted, cursorId, cursorValue, size)
    }
    
}