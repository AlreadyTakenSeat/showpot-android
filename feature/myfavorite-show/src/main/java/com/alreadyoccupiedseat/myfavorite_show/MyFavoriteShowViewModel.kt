package com.alreadyoccupiedseat.myfavorite_show

import androidx.lifecycle.ViewModel
import com.alreadyoccupiedseat.common.utiils.errorLog
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.data.toApiErrorResult
import com.alreadyoccupiedseat.model.Show
import com.alreadyoccupiedseat.model.show.InterestedData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

sealed interface MyFavoriteShowEvent {
    data object Idle : MyFavoriteShowEvent
}

data class MyFavoriteShowState(
    val showList: List<Show> = emptyList(),
    val interestedShowList: List<InterestedData> = emptyList()
)

@HiltViewModel
class MyFavoriteShowViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel(), ContainerHost<MyFavoriteShowState, MyFavoriteShowEvent> {

    override val container: Container<MyFavoriteShowState, MyFavoriteShowEvent> =
        container(MyFavoriteShowState())

    /** 관심 공연 목록 조회 ***/
    fun getInterestedShow() = intent {

        val result = showRepository.getInterestedShowList(
            size = 30
        )

        reduce {
            state.copy(
                interestedShowList = result
            )
        }
    }

    /** 관심 공연 삭제 ***/
    fun deleteMyFavoriteShow(showId: String) = intent {

        val result = showRepository.registerShowUnInterest(showId = showId)

        result.onSuccess {
            reduce {
                state.copy(
                    interestedShowList = state.interestedShowList.filter { it.id != showId }
                )
            }
        }.onFailure {
            errorLog(it.toApiErrorResult().message)
        }

    }

}