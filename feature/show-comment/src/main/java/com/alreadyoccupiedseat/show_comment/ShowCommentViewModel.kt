package com.alreadyoccupiedseat.show_comment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface ShowCommentEvent {
    data object Idle : ShowCommentEvent
}

data class ShowCommentState(
    val isLoading: Boolean = false,
)

@HiltViewModel
class ShowCommentViewModel @Inject constructor(

) : ViewModel(), ContainerHost<ShowCommentState, ShowCommentEvent> {

    override val container: Container<ShowCommentState, ShowCommentEvent> =
        container(ShowCommentState())

    init {

    }

}
