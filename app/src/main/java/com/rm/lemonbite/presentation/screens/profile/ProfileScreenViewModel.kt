package com.rm.lemonbite.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.lemonbite.data.local.preferences.UserPreferencesRepository
import com.rm.lemonbite.data.local.preferences.model.NotificationStatus
import com.rm.lemonbite.presentation.utils.NotificationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    val notificationList: StateFlow<List<NotificationStatus>> = combine(
        userPreferencesRepository.emailOrderStatus,
        userPreferencesRepository.emailNewsLetter,
        userPreferencesRepository.emailSpecialOffers,
        userPreferencesRepository.emailPasswordChanges,
    ){ orderStatus, newsletter, specialOffers, passwordChanges ->
        listOf(
            NotificationStatus(NotificationType.ORDER_STATUS, orderStatus),
            NotificationStatus(NotificationType.NEWSLETTER, newsletter),
            NotificationStatus(NotificationType.SPECIAL_OFFERS, specialOffers),
            NotificationStatus(NotificationType.PASSWORD_CHANGES, passwordChanges),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

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

    fun onCheckChange(notificationStatus: NotificationStatus) {
        viewModelScope.launch {
            when(notificationStatus.notificationType) {
                NotificationType.ORDER_STATUS -> userPreferencesRepository.saveEmailOrderStatus(notificationStatus.status)
                NotificationType.NEWSLETTER -> userPreferencesRepository.saveEmailNewsLetterStatus(notificationStatus.status)
                NotificationType.SPECIAL_OFFERS -> userPreferencesRepository.saveEmailSpecialOffers(notificationStatus.status)
                NotificationType.PASSWORD_CHANGES -> userPreferencesRepository.saveEmailPasswordChanges(notificationStatus.status)
            }
        }
    }
}
