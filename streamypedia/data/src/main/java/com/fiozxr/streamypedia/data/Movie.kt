package com.fiozxr.streamypedia.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val filePath: String,
    val posterUrl: String? = null,
    val needsConversion: Boolean = false
)
