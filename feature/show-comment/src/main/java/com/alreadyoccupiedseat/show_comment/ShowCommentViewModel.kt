package com.alreadyoccupiedseat.show_comment

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utils.errorLog
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.comment.CommentRepository
import com.alreadyoccupiedseat.data.login.LoginRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.model.comment.CommentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface ShowCommentEvent {
    data object Idle : ShowCommentEvent

    data object PostCommentSuccess : ShowCommentEvent
}

data class ShowCommentState(
    val comments: List<CommentResponse> = emptyList(),
    val showId: String = String.EMPTY,
    val inputtedComment: String = String.EMPTY,
    val nickName: String = String.EMPTY,
    val isNewCommentLoading: Boolean = false,
    val cursorId: String? = null,
    val cursorIdTop: String? = null,
    val hasNext: Boolean = false,
    val hasNextTop: Boolean = false
)

@HiltViewModel
class  ShowCommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val loginRepository: LoginRepository
) : ViewModel(), ContainerHost<ShowCommentState, ShowCommentEvent> {

    override val container: Container<ShowCommentState, ShowCommentEvent> =
        container(ShowCommentState())

    fun setShowId(showId: String) = intent {
        reduce {
            state.copy(
                showId = showId
            )
        }
    }

    // TODO: Considering scalability. e.g contentType, parentId(Reply)
    fun postComment() = intent {

        val result = commentRepository.postComment(
            refId = state.showId,
            commentType = "SHOW",
            content = state.inputtedComment,
            parentId = String.EMPTY
        )

        result.onSuccess {
            postSideEffect(ShowCommentEvent.PostCommentSuccess)
            reduce {
                state.copy(
                    comments = it.data + state.comments,
                    cursorId = it.cursor.id,
                    hasNext = it.hasNext
                )
            }
        }.onFailure {
            this@ShowCommentViewModel.errorLog(it.toApiErrorResult().message)
        }
    }

    fun changeInputtedComment(inputtedComment: String) = intent {
        reduce {
            state.copy(
                inputtedComment = inputtedComment
            )
        }
    }

    fun getComments() = intent {
        val result = commentRepository.getComments(
            refId = state.showId,
            type = "SHOW",
            isInverted = false,
            cursorId = null,
            cursorValue = null,
            size = 10
        )

        result.onSuccess {
            reduce {
                state.copy(
                    comments = it.data.reversed(),
                    cursorId = it.cursor.id,
                    hasNext = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

        val resultTop = commentRepository.getComments(
            refId = state.showId,
            type = "SHOW",
            isInverted = true,
            cursorId = null,
            cursorValue = null,
            size = 10
        )

        resultTop.onSuccess {
            reduce {
                state.copy(
                    cursorIdTop = it.cursor.id,
                    hasNextTop = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

    }

    fun getNickName() = intent {
        loginRepository.getProfile().onSuccess { profile ->
            reduce {
                state.copy(nickName = profile.nickname)
            }
        }
    }

    // TODO: Consider direction
    fun loadMore() = intent {

        if (state.hasNext.not()) {
            reduce {
                state.copy(
                    isNewCommentLoading = false
                )
            }
        }

        reduce {
            state.copy(
                isNewCommentLoading = true
            )
        }

        val result = commentRepository.getComments(
            refId = state.showId,
            type = "SHOW",
            isInverted = false,
            cursorId = state.cursorId,
            size = 10
        )

        result.onSuccess {
            reduce {
                state.copy(
                    comments = it.data.reversed() + state.comments,
                    cursorId = it.cursor.id,
                    hasNext = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

        reduce {
            state.copy(
                isNewCommentLoading = false
            )
        }
    }

    fun loadMoreTop() = intent {

        if (state.hasNextTop.not()) {
            return@intent
        }

        val result = commentRepository.getComments(
            refId = state.showId,
            type = "SHOW",
            isInverted = true,
            cursorId = state.cursorIdTop,
            size = 10
        )

        result.onSuccess {
            reduce {
                state.copy(
                    comments = state.comments + it.data.reversed(),
                    cursorIdTop = it.cursor.id,
                    hasNextTop = it.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

    }

}
