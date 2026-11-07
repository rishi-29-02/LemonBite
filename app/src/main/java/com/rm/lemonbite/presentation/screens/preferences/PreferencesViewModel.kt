package com.rm.lemonbite.presentation.screens.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val courses = listOf(
        "Starters", "Mains", "Desserts", "Sides"
    )

    private var _selected = MutableStateFlow<Set<String>>(emptySet())

    val selected = _selected.asStateFlow()

    fun removeCourse(course: String) = _selected.update { it - course }

    fun addCourse(course: String) = _selected.update { it + course }

    fun setOnboardingData(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                userPreferencesRepository.setPrefs(_selected.value)
                userPreferencesRepository.setOnboardingCompleted(true)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}