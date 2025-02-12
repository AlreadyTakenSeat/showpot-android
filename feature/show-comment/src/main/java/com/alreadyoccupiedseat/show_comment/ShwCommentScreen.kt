package com.alreadyoccupiedseat.show_comment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.orbitmvi.orbit.compose.collectAsState

data class Comment(
    val id: Int,
    val profileImageUrl: String,
    val content: String,
    val time: String // 밀리 세컨드 변경 필요
)

val dummyComments: List<Comment> = List(100) { index ->
    Comment(
        id = index + 1,
        profileImageUrl = "https://example.com/profile/${index + 1}.png",
        content = "이것은 ${index + 1}번째 댓글입니다.",
        time = "12:${(index % 60).toString().padStart(2, '0')}"
    )
}

@Composable
fun ShowCommentScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<ShowCommentViewModel>()
    val state by viewModel.collectAsState()
    ShowCommentContentScreen(
        state = state,
        onBackClicked = {
            navController.popBackStack()
        },
        loadMore = {

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
) {

}