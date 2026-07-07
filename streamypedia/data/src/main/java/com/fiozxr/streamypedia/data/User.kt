package com.fiozxr.streamypedia.data

// Basic entity representation (Room annotations would be added later)
data class User(
    val id: Int,
    val name: String,
    val avatarUrl: String? = null
)
