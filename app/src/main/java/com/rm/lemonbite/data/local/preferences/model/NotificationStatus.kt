package com.rm.lemonbite.data.local.preferences.model

import com.rm.lemonbite.presentation.utils.NotificationType

data class NotificationStatus(
    val notificationType: NotificationType,
    val status: Boolean
)
