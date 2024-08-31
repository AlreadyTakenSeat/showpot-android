package com.alreadyoccupiedseat.myfavorite_show

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

    init {
        getInterestedShow()
    }

    /** 관심 공연 ***/
    private fun getInterestedShow() {
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

    fun deleteMyFavoriteShow(id: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showList = _state.value.showList.filter { it.id != id }
                )
            )
        }
    }

}