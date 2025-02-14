package com.alreadyoccupiedseat.show_comment

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utils.errorLog
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.data.comment.CommentRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
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
    val showId: String = String.EMPTY,
    val inputtedComment: String = String.EMPTY,
)

@HiltViewModel
class ShowCommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository
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

}
