package com.rm.lemonbite.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AddOnDto(val name: String = "", val price: Double = 0.0)
