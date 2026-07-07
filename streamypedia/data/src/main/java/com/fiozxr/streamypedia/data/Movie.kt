package com.fiozxr.streamypedia.data

// Basic entity representation (Room annotations would be added later)
data class Movie(
    val id: Int,
    val title: String,
    val filePath: String,
    val posterUrl: String? = null,
    val needsConversion: Boolean = false
)
