package com.alreadyoccupiedseat.entire_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.show.ShowPreview
import com.alreadyoccupiedseat.model.show.ShowType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface EntireShowEvent {
    data object Error: EntireShowEvent
}

data class EntireShowState(
    val entireShowList: List<ShowPreview> = emptyList(),
)

@HiltViewModel
class EntireShowViewModel @Inject constructor(
    private val showRepository: ShowRepository
): ViewModel() {

    private val _state = MutableStateFlow(EntireShowState())
    val state = _state

    init {
        getEntireShow()
    }

    /** 전체 공연 목록 가져오기 ***/
    private fun getEntireShow() {
        viewModelScope.launch {
            val tempRequestSize = 30
            showRepository.getEntireShow(
                sort = ShowType.POPULAR.text,
                onlyOpenSchedule = false,
                size = tempRequestSize,
            ).let { result ->

                _state.value = _state.value.copy(
                    entireShowList = result
                )
            }
        }
    }

}
