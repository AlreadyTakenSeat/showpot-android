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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alreadyoccupiedseat.common.InversePullToRefreshBox
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.comment.MyCommentItem
import com.alreadyoccupiedseat.designsystem.component.comment.OtherCommentItem
import com.alreadyoccupiedseat.designsystem.component.inputBox.ShowCommentInputBox
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
        viewModel.getComments()
        viewModel.getNickName()
    }

    ShowCommentContentScreen(
        state = state,
        onBackClicked = {
            navController.popBackStack()
        },
        loadMore = {
            viewModel.loadMore()
        },
        onInputTextFieldChanged = {
            viewModel.changeInputtedComment(it)
        },
        onSendButtonClicked = {
            viewModel.postComment()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowCommentContentScreen(
    modifier: Modifier = Modifier,
    state: ShowCommentState,
    onBackClicked: () -> Unit,
    loadBefore: () -> Unit = {},
    loadMore: () -> Unit = {},
    onInputTextFieldChanged: (String) -> Unit = {},
    onSendButtonClicked: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

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

        val pullToRefreshState = rememberPullToRefreshState()

        InversePullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = pullToRefreshState,
            isRefreshing = state.isNewCommentLoading,
            onRefresh = { loadMore() },
            contentAlignment = Alignment.Center,
            indicatorContainerColor = Color.Transparent,
            indicatorColor = ShowpotColor.MainOrange,
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(paddingValues)
                    .padding(bottom = 60.dp),
            ) {
                // Comment List
                state.comments.forEach { comment ->
                    item {

                        if (comment.userName != state.nickName) {
                            OtherCommentItem(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),
                                // TODO: replace with real data
                                // profileUrl = comment.profileURL,
                                profileUrl = "https://picsum.photos/200",
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
                modifier = Modifier.fillMaxSize()
                ,
                contentAlignment = Alignment.BottomCenter
            ) {
                ShowCommentInputBox(
                    inputText = state.inputtedComment,
                    hint = "티켓팅 성공을 기원해 보세요 | ex. A구역 1열 간다",
                    onValueChange = {
                        onInputTextFieldChanged(it)
                    },
                    onSendButtonClicked = {
                        onSendButtonClicked()
                        onInputTextFieldChanged(String.EMPTY)
                        focusManager.clearFocus()
                    }
                )
            }
        }

    }

}