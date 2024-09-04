package com.alreadyoccupiedseat.myfavorite_show

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.Show
import com.alreadyoccupiedseat.model.show.InterestedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyFavoriteShowState(
    val showList: List<Show> = emptyList(),
    val interestedShowList: List<InterestedData> = emptyList()
)

@HiltViewModel
class MyFavoriteShowViewModel @Inject constructor(
    private val showRepository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MyFavoriteShowState())
    val state = _state

    /** 관심 공연 목록 조회 ***/
    fun getInterestedShow() {
        viewModelScope.launch {
            showRepository.getInterestedShowList(
                size = 100
            ).let {
                _state.value = _state.value.copy(
                    interestedShowList = it
                )
            }
        }
    }

    /** 관심 공연 삭제 ***/
    fun deleteMyFavoriteShow(showId: String) {
        viewModelScope.launch {
            val notInterest = !showRepository.registerShowInterest(showId = showId)
            if (notInterest) {
                _state.value = _state.value.copy(
                    interestedShowList = _state.value.interestedShowList.filter { it.id != showId }
                )
            } else {
                Log.e("MyFavoriteShowViewModel", "deleteMyFavoriteShow failed")
            }
        }
    }

}