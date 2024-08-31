package com.alreadyoccupiedseat.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alreadyoccupiedseat.datastore.AccountDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsScreenState(
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore
): ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            accountDataStore.getAccessTokenFlow().collectLatest { token ->
                _state.value = _state.value.copy(isLoggedIn = token != null)
            }
        }
    }


}