package com.alreadyoccupiedseat.entire_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowRepository
import com.alreadyoccupiedseat.model.show.Shows
import com.alreadyoccupiedseat.model.show.Shows.Companion.POPULAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface EntireShowEvent {
    data object Error: EntireShowEvent
}

data class EntireShowState(
    val entireShowList: Shows = Shows(data = emptyList(), hasNext = false, size = 0),
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
            val tempRequestSize = 100
            showRepository.getEntireShow(
                sort = POPULAR,
                onlyOpenSchedule = false,
                size = tempRequestSize,
            ).let { result ->
                _state.value = _state.value.copy(
                    entireShowList = Shows(
                        data = result,
                        hasNext = true,
                        size = result.size
                    )
                )
            }
        }
    }

}
