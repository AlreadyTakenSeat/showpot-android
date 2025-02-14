package com.alreadyoccupiedseat.entire_show

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utils.errorLog
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.show.ShowType
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface EntireShowEvent {
    data object Error : EntireShowEvent
}

data class EntireShowState(
    val entireShowList: List<ShowPreview> = emptyList(),
    val pageId: String? = null,
    val hasNext: Boolean = false
)

@HiltViewModel
class EntireShowViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel(), ContainerHost<EntireShowState, EntireShowEvent> {

    override val container = container<EntireShowState, EntireShowEvent>(EntireShowState())
    init {
        getEntireShow()
    }

    /** 전체 공연 목록 가져오기 ***/
    private fun getEntireShow() = intent {

        val tempRequestSize = 30

        val result = showRepository.getEntireShow(
            sort = ShowType.RECENT.text,
            onlyOpenSchedule = false,
            size = tempRequestSize,
        )

        result.onSuccess { pagingData ->
            reduce {
                state.copy(
                    entireShowList = pagingData.data,
                    pageId = pagingData.cursor.id,
                    hasNext = pagingData.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }
    }

    fun loadNextPage() = intent {

        if (!state.hasNext) return@intent

        val tempRequestSize = 30

        val result = showRepository.getEntireShow(
            sort = ShowType.RECENT.text,
            onlyOpenSchedule = false,
            size = tempRequestSize,
            cursorId  = state.pageId
        )

        result.onSuccess { pagingData ->
            reduce {
                state.copy(
                    entireShowList = state.entireShowList + pagingData.data,
                    pageId = pagingData.cursor.id,
                    hasNext = pagingData.hasNext
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }
    }

}
