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
    val isLoading: Boolean = false,
    val comments: List<CommentResponse> = emptyList(),
    val showId: String = String.EMPTY,
    val inputtedComment: String = String.EMPTY,
    val nickName: String = String.EMPTY
)

@HiltViewModel
class ShowCommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val loginRepository: LoginRepository
) : ViewModel(), ContainerHost<ShowCommentState, ShowCommentEvent> {

    override val container: Container<ShowCommentState, ShowCommentEvent> =
        container(ShowCommentState())

    fun setShowId(showId: String) = intent{
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
            size = 30)

        result.onSuccess {
            reduce {
                state.copy(
                    comments = it.data
                )}
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

}
