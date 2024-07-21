package com.alreadyoccupiedseat.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.core.extension.EMPTY
import com.alreadyoccupiedseat.datastore.SearchHistoryDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchScreenEvent {
    data object Idle : SearchScreenEvent
}

data class SearchScreenState(
    val searchHistory: List<String> = emptyList(),
    val inputText : String = String.EMPTY
)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryDataStore: SearchHistoryDataStore
): ViewModel() {

    private var _state = MutableStateFlow<SearchScreenState>(SearchScreenState())
    val state = _state

    private var _event = MutableStateFlow<SearchScreenEvent>(SearchScreenEvent.Idle)
    val event = _event

    fun updateInputText(inputText: String) {
        _state.value = _state.value.copy(inputText = inputText)
    }

    fun initInputText() {
        _state.value = _state.value.copy(inputText = String.EMPTY)
    }

    fun updateSearchHistories() {
        viewModelScope.launch {
            val newKeyword = _state.value.inputText
            val updatedSearchHistory = searchHistoryDataStore.updateSearchedKeyword(newKeyword)
            _state.value = _state.value.copy(searchHistory = updatedSearchHistory.reversed())
        }
    }
}