package com.rm.lemonbite.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    fun updateFirstName(name: String) {
        _firstName.value = name
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun verifyName() : Boolean = firstName.value.isNotEmpty()

    fun verifyEmail() : Boolean {
        val e = email.value.trim()
        return e.contains("@")
                && e.contains(".")
                && e.indexOf("@") > 0
                && e.lastIndexOf(".") > e.indexOf("@")
                && !e.endsWith(".")
    }

    fun saveUserData(
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                userPreferencesRepository.saveUserData(
                    firstName.value, email.value
                )
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}