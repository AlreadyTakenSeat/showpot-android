package com.alreadyoccupiedseat.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alreadyoccupiedseat.designsystem.ShowpotColor
import com.alreadyoccupiedseat.designsystem.component.SearchHistoryChip
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_Regular
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_B1_SemiBold
import com.alreadyoccupiedseat.designsystem.typo.korean.ShowPotKoreanText_H2

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecentSearchHistorySection(
    searchHistories: List<String>,
    onChipClicked: (String) -> Unit,
    onDeleteAllClicked: () -> Unit,
    onDeleteHistoryClicked: (String) -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShowPotKoreanText_H2(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.recent_serach_history), color = ShowpotColor.Gray100
        )

        ShowPotKoreanText_B1_Regular(
            modifier = Modifier.clickable {
                onDeleteAllClicked()
            },
            text = stringResource(R.string.delete_all), color = ShowpotColor.Gray400
        )
    }

    if (searchHistories.isEmpty()) {
        ShowPotKoreanText_B1_SemiBold(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp), text = "검색 기록이 없어요", color = ShowpotColor.Gray400,
            textAlign = TextAlign.Center
        )
    } else {
        FlowRow(
            Modifier.padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            searchHistories.forEach {
                SearchHistoryChip(it,
                    onChipClicked = { chipText ->
                        onChipClicked(chipText)
                    }
                ) { searchedText ->
                    onDeleteHistoryClicked(searchedText)
                }
            }
        }
    }

}
