package com.alreadyoccupiedseat.show_comment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.inputBox.ShowCommentInputBox
import com.alreadyoccupiedseat.model.comment.CommentResponse
import com.alreadyoccupiedseat.show_comment.components.MyCommentItem
import com.alreadyoccupiedseat.show_comment.components.OtherCommentItem
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ShowCommentScreen(
    navController: NavController,
    showId: String
) {

    val viewModel = hiltViewModel<ShowCommentViewModel>()
    val state by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setShowId(showId)
    }

    ShowCommentContentScreen(
        state = state,
        onBackClicked = {
            navController.popBackStack()
        },
        loadMore = {

        },
        onInputTextFieldChanged = {
            viewModel.changeInputtedComment(it)
        },
        onSendButtonClicked = {
            viewModel.postComment()
        }
    )
}

@Composable
private fun ShowCommentContentScreen(
    modifier: Modifier = Modifier,
    state: ShowCommentState,
    onBackClicked: () -> Unit,
    loadBefore: () -> Unit = {},
    loadMore: () -> Unit = {},
    onInputTextFieldChanged: (String) -> Unit = {},
    onSendButtonClicked : () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    val dummyComment = listOf(
        CommentResponse.Dummy.copy(
            createdAt = "2025-2-13 14:25"
        ),
        CommentResponse.Dummy.copy(
                createdAt = "2025-2-13 14:35"
                ),
        CommentResponse.Dummy.copy(
            userName = "myNickName",
            createdAt = "2025-2-13 14:45"
        ),
        CommentResponse.Dummy.copy(
            createdAt = "2025-2-13 15:25"
        ),
        CommentResponse.Dummy.copy(
            createdAt = "2025-2-13 15:55"
        ),
        CommentResponse.Dummy.copy(
            createdAt = "2025-2-13 16:12"
        ),
        CommentResponse.Dummy.copy(
            createdAt = "2025-2-13 17:00"
        ),
    )

    Scaffold(
        containerColor = ShowpotColor.Gray700,
        topBar = {
            Column {

                Spacer(modifier = Modifier.height(12.dp))

                ShowCommentTopBar(
                    onBackClicked = onBackClicked
                )

            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .imePadding()
                .padding(paddingValues),
        ) {
            // Comment List
            dummyComment.forEach { comment ->
                item {

                    // TODO: valid with my actual name
                    if (comment.userName != "myNickName") {
                        OtherCommentItem(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp),
                            profileUrl = comment.profileURL,
                            userName = comment.userName,
                            content = comment.content,
                            createdAt = comment.createdAt
                        ) {
                            // onIconClicked
                        }
                    } else {
                        MyCommentItem(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp),
                            content = comment.content,
                            createdAt = comment.createdAt
                        ) {
                            // onIconClicked
                        }
                    }

                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ShowCommentInputBox(
                inputText = state.inputtedComment,
                onValueChange = {
                    onInputTextFieldChanged(it)
                },
                hint = "티켓팅 성공을 기원해 보세요 | ex. A구역 1열 간다",
                onSendButtonClicked = {
                    onSendButtonClicked()
                    onInputTextFieldChanged(String.EMPTY)
                    focusManager.clearFocus()
                }
            )
        }
    }

}