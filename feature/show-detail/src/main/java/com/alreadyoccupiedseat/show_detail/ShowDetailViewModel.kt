package com.alreadyoccupiedseat.show_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.data.show.ShowDataSource
import com.alreadyoccupiedseat.model.show.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShowDetailState(
    val showDetail: ShowDetail? = null,
)

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val showDataSource: ShowDataSource
): ViewModel()    {

    private val _state = MutableStateFlow(ShowDetailState())
    val state = _state

    fun getShowDetail(showId: String) {
        viewModelScope.launch {
            val result = showDataSource.getShowDetail(showId)
            _state.value = ShowDetailState(showDetail = result)
        }
    }
}