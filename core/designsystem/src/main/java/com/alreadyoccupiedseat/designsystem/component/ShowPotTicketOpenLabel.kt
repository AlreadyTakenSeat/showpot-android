package com.alreadyoccupiedseat.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.R
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B2_Regular

@Preview
@Composable
fun ShowPotTicketOpenLabel(modifier: Modifier = Modifier) {
    ShowPotTicketOpenLabel(hasTicketingOpen = true)
}

@Preview
@Composable
fun ShowPotTicketOpenSoonLabel(modifier: Modifier = Modifier) {
    ShowPotTicketOpenLabel(hasTicketingOpen = false)
}


@Composable
fun ShowPotTicketOpenLabel(
    hasTicketingOpen: Boolean,
) {
    val color = if (hasTicketingOpen) {
        ShowpotColor.MainBlue
    } else {
        ShowpotColor.MainYellow
    }
    val text = if (hasTicketingOpen) {
        stringResource(id = R.string.now_selling)
    } else {
        stringResource(id = R.string.open_soon)
    }
    Box(
        modifier = Modifier
            .border(0.5.dp, color, shape = RoundedCornerShape(size = 2.dp))
    ) {
        ShowPotKoreanText_B2_Regular(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .align(Alignment.Center),
            text = text,
            color = color,
        )
    }
}