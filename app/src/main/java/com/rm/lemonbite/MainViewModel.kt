package com.rm.lemonbite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.preferences.UserPreferencesRepository
import com.rm.lemonbite.domain.StartState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    val startState: StateFlow<StartState> =
        userPreferencesRepository.onBoardingCompleted
            .map { done -> if (done) StartState.Home else StartState.Welcome }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                StartState.Loading,
            )
}
