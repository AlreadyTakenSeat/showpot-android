package com.alreadyoccupiedseat.data.comment

import com.alreadyoccupiedseat.data.getResult
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.comment.CommentResponse
import com.alreadyoccupiedseat.model.comment.PostCommentRequest
import com.alreadyoccupiedseat.model.comment.ReportOrBlockCommentRequest
import com.alreadyoccupiedseat.network.CommentService

class CommentDataSourceImpl(
    private val commentService: CommentService
) : CommentDataSource {
    override suspend fun postComment(
        refId: String,
        commentType: String,
        content: String,
        parentId: String
    ): Result<Unit> {
        return runCatching {
            commentService.postComment(PostCommentRequest(
                refId = refId,
                commentType = commentType,
                content = content,
                parentId = parentId))
        }
    }

    override suspend fun reportOrBlockComment(
        commentId: String,
        directInput: String,
        reportType: String
    ): Result<Unit> {
        return runCatching {
            commentService.reportOrBlockComment(commentId, ReportOrBlockCommentRequest(directInput, reportType))
        }
    }

    override suspend fun deleteComment(commentId: String): Result<Unit> {
        return runCatching {
            commentService.deleteComment(commentId)
        }
    }

    override suspend fun getComments(refId: String, type: String, isInverted: Boolean, cursorId: String?, cursorValue: String?, size: Int): Result<PagingData<CommentResponse>> {
        return runCatching {
            commentService.getComments(refId, type, isInverted, cursorId, cursorValue, size).getResult {
                it.data
            }
        }
    }
}
