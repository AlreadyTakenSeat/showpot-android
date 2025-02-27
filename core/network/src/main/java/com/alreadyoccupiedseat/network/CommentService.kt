package com.alreadyoccupiedseat.network

import com.alreadyoccupiedseat.model.ApiResult
import com.alreadyoccupiedseat.model.Cursor
import com.alreadyoccupiedseat.model.PagingData
import com.alreadyoccupiedseat.model.comment.CommentResponse
import com.alreadyoccupiedseat.model.comment.PostCommentRequest
import com.alreadyoccupiedseat.model.comment.ReportOrBlockCommentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {

    @POST("api/v1/comments")
    suspend fun postComment(
        @Body postCommentRequest: PostCommentRequest
    ): Response<ApiResult<PagingData<CommentResponse>>>

    @POST("api/v1/comments/{commentId}/report")
    suspend fun reportOrBlockComment(
        @Path("commentId") commentId: String,
        @Body reportOrBlockCommentRequest: ReportOrBlockCommentRequest
    ): Response<ApiResult<Unit>>

    @DELETE("api/v1/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: String
    ): Response<ApiResult<Unit>>

    @GET("api/v1/comments/{refId}")
    suspend fun getComments(
        @Path("refId") refId: String,
        @Query("type") type: String,
        @Query("isInverted") isInverted: Boolean,
        @Query("cursorId") cursorId: String? = null,
        @Query("cursorValue") cursorValue: String? = null,
        @Query("size") size: Int,
    ): Response<ApiResult<PagingData<CommentResponse>>>
}