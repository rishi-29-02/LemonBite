package com.rm.lemonbite.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    val userName : StateFlow<String> = userPreferencesRepository.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ""
        )

    val email : StateFlow<String> = userPreferencesRepository.userEmail
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ""
        )
}
