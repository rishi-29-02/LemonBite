package com.rm.lemonbite.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.preferences.UserPreferencesRepository
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.domain.repository.MenuRepository
import com.rm.lemonbite.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel
@Inject
constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {
    val coursePrefsList: StateFlow<Set<String>> =
        userPreferencesRepository.coursesPrefs
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptySet(),
            )

    private var _selectedCourses = MutableStateFlow<MutableList<String>>(mutableListOf())
    val selectedCourses = _selectedCourses.asStateFlow()

    private val _menuUiState = MutableStateFlow<UiState<List<MenuItem>>>(UiState.Loading)
    val menuUiState: StateFlow<UiState<List<MenuItem>>> = _menuUiState.asStateFlow()

    init {
        loadMenu()
    }

    fun loadMenu() {
        viewModelScope.launch {
            _menuUiState.value = UiState.Loading

            menuRepository
                .getMenu()
                .onSuccess {
                    _menuUiState.value = UiState.Success(it)
                }.onFailure {
                    _menuUiState.value =
                        UiState.Error(it.localizedMessage ?: "An error occurred")
                }
        }
    }

    fun removeFromSelected(course: String) {
        _selectedCourses.value = (_selectedCourses.value - course).toMutableList()
    }

    fun addToSelected(course: String) {
        _selectedCourses.value = (_selectedCourses.value + course).toMutableList()
    }
}
